package com.db8.popupcoffee.rental.service;

import com.db8.popupcoffee.rental.repository.SpaceRentalAgreementRepository;
import com.db8.popupcoffee.space.repository.SpaceRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final SpaceRentalAgreementRepository spaceRentalAgreementRepository;
    private final SpaceRepository spaceRepository;

    @Transactional(readOnly = true)
    public int countAvailableSpaces(LocalDate date) {
        return (int) spaceRepository.count()
            - spaceRentalAgreementRepository.countBySpecificDate(date);
    }
}
