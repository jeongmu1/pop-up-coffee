package com.db8.popupcoffee.rental.repository;

import com.db8.popupcoffee.rental.domain.SpaceRentalAgreement;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceRentalAgreementRepository extends
    ListCrudRepository<SpaceRentalAgreement, Long>,
    SpaceRentalAgreementCustomRepository {

}
