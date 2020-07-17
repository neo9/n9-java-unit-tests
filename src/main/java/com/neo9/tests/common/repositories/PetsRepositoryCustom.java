package com.neo9.tests.common.repositories;

import com.neo9.tests.common.entities.Pet;
import reactor.core.publisher.Mono;

public interface PetsRepositoryCustom {
	Mono<Pet> findOldestPet();
}
