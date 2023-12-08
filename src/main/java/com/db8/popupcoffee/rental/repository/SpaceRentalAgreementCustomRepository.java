package com.db8.popupcoffee.rental.repository;

import java.time.LocalDate;

public interface SpaceRentalAgreementCustomRepository {

    int countBySpecificDate(LocalDate date);
}
