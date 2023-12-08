package com.db8.popupcoffee.rental.repository;

import com.db8.popupcoffee.rental.domain.SpaceRentalAgreement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceRentalAgreementRepository extends CrudRepository<SpaceRentalAgreement, Long>,
    SpaceRentalAgreementCustomRepository {


}
