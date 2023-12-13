package com.db8.popupcoffee.space.repository;

import com.db8.popupcoffee.space.domain.Space;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceRepository extends ListCrudRepository<Space, Long>, SpaceCustomRepository {

}
