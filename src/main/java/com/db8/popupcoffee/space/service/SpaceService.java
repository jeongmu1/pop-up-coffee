package com.db8.popupcoffee.space.service;

import com.db8.popupcoffee.reservation.controller.dto.response.SimpleReservationInfo;
import com.db8.popupcoffee.reservation.domain.FixedReservation;
import com.db8.popupcoffee.reservation.domain.FlexibleReservation;
import com.db8.popupcoffee.reservation.domain.FlexibleReservationStatus;
import com.db8.popupcoffee.reservation.repository.FixedReservationRepository;
import com.db8.popupcoffee.reservation.repository.FlexibleReservationRepository;
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
                .filter(it -> it.getTemporalSpace().equals(space))
                .map(SimpleReservationInfo::from).toList();
            var flexibleInfos = flexibleReservations.stream()
                .filter(it -> it.getTemporalSpace().equals(space))
                .map(SimpleReservationInfo::from).toList();

            return new SpaceReservations(space.getNumber(),
                Stream.concat(fixedInfos.stream(), flexibleInfos.stream()).sorted(
                    Comparator.comparing(SimpleReservationInfo::endDate)).toList());
        }).toList();
    }
}
