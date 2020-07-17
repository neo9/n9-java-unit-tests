package com.neo9.tests.common.repositories;

import com.neo9.tests.common.entities.Pet;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PetsRepository extends ReactiveMongoRepository<Pet, String>, PetsRepositoryCustom {
}
