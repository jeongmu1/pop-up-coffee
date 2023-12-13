package com.db8.popupcoffee.space.service;

import com.db8.popupcoffee.reservation.controller.dto.response.SimpleReservationInfo;
import com.db8.popupcoffee.reservation.domain.FixedReservation;
import com.db8.popupcoffee.reservation.domain.FixedReservationStatus;
import com.db8.popupcoffee.reservation.domain.FlexibleReservation;
import com.db8.popupcoffee.reservation.domain.FlexibleReservationStatus;
import com.db8.popupcoffee.reservation.repository.FixedReservationRepository;
import com.db8.popupcoffee.reservation.repository.FlexibleReservationRepository;
import com.db8.popupcoffee.space.controller.dto.request.ReservationIdDto;
import com.db8.popupcoffee.space.controller.dto.request.UpdateAssignmentRequest;
import com.db8.popupcoffee.space.controller.dto.response.SpaceInfo;
import com.db8.popupcoffee.space.controller.dto.response.SpaceReservations;
import com.db8.popupcoffee.space.domain.Space;
import com.db8.popupcoffee.space.repository.SpaceRepository;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SpaceService {

    private final SpaceRepository spaceRepository;
    private final FixedReservationRepository fixedReservationRepository;
    private final FlexibleReservationRepository flexibleReservationRepository;

    public List<SpaceInfo> findAllSpaces() {
        return spaceRepository.findAll().stream().map(SpaceInfo::from).toList();
    }

    @Transactional(readOnly = true)
    public List<SpaceReservations> getReservationInfosOfSpaces() {
        List<Space> spaces = spaceRepository.findAll();
        LocalDate today = LocalDate.now();
        List<FixedReservation> fixedReservations = fixedReservationRepository.findByEndDateAfterAndFromFlexibleReservation(
            today, false);
        List<FlexibleReservation> flexibleReservations = flexibleReservationRepository.findByTemporalRentalEndDateAfterAndStatusIn(
            today, FlexibleReservationStatus.getStatusOverTemporaryFixed());
        return spaces.stream().map(space ->
        {
            var fixedInfos = fixedReservations.stream()
                .filter(it -> it.getTemporalSpace() != null && it.getTemporalSpace().equals(space))
                .map(SimpleReservationInfo::from).toList();
            var flexibleInfos = flexibleReservations.stream()
                .filter(it -> it.getTemporalSpace() != null && it.getTemporalSpace().equals(space))
                .map(SimpleReservationInfo::from).toList();

            return new SpaceReservations(space.getNumber(),
                Stream.concat(fixedInfos.stream(), flexibleInfos.stream()).sorted(
                    Comparator.comparing(SimpleReservationInfo::endDate)).toList());
        }).toList();
    }

    @Transactional
    public void unAssignSpace(ReservationIdDto request) {
        if (request.fromFlexible()) {
            unAssignFlexibleReservation(request.id());
        } else {
            unAssignFixedReservation(request.id());
        }
    }

    @Transactional
    public void updateAssignment(UpdateAssignmentRequest request) {
        Space space = spaceRepository.findById(request.spaceId()).orElseThrow();
        if (request.fromFlexible()) {
            var flexible = flexibleReservationRepository.findById(request.id()).orElseThrow();
            flexible.setTemporalRentalStartDate(request.startDate());
            flexible.setTemporalRentalEndDate(request.endDate());
            flexible.setStatus(FlexibleReservationStatus.SPACE_TEMPORARY_FIXED);
            flexible.setTemporalSpace(space);
        } else {
            var fixed = fixedReservationRepository.findById(request.id()).orElseThrow();
            fixed.setTemporalSpace(space);
            fixed.setStartDate(request.startDate());
            fixed.setEndDate(request.endDate());
            fixed.setStatus(FixedReservationStatus.SPACE_TEMPORARY_FIXED);
        }
    }

    private void unAssignFlexibleReservation(Long id) {
        FlexibleReservation flexible = flexibleReservationRepository.findById(id).orElseThrow();

        if (flexible.getStatus().equals(FlexibleReservationStatus.RESERVATION_FIXED)) {
            throw new IllegalArgumentException("이미 확정되었습니다.");
        }

        flexible.setTemporalSpace(null);
        flexible.setStatus(FlexibleReservationStatus.WAITING);
    }

    private void unAssignFixedReservation(Long id) {
        FixedReservation fixed = fixedReservationRepository.findById(id).orElseThrow();

        if (fixed.getStatus().equals(FixedReservationStatus.FIXED)) {
            throw new IllegalArgumentException("이미 확정되었습니다.");
        }

        fixed.setTemporalSpace(null);
        fixed.setStatus(FixedReservationStatus.SPACE_AWAITING);
    }
}
