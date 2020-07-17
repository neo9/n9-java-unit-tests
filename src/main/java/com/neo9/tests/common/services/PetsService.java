package com.neo9.tests.common.services;

import com.neo9.tests.common.models.PetDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PetsService {
	Mono<PetDto> insert(PetDto petDto);

	Mono<PetDto> findOldest();

	Flux<PetDto> findAll();
}
