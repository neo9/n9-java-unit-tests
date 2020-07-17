package com.neo9.tests.common.services.impl;

import com.neo9.tests.common.mappers.PetMapper;
import com.neo9.tests.common.models.PetDto;
import com.neo9.tests.common.repositories.PetsRepository;
import com.neo9.tests.common.services.PetsService;
import com.neo9.tests.exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PetsServiceImpl implements PetsService {
	private final PetsRepository petsRepository;
	private final PetMapper petMapper;

	public PetsServiceImpl(PetsRepository petsRepository, PetMapper petMapper) {
		this.petsRepository = petsRepository;
		this.petMapper = petMapper;
	}

	@Override
	public Mono<PetDto> insert(PetDto petDto) {
		return petsRepository.save(petMapper.toEntity(petDto))
						.map(petMapper::toDto);
	}

	@Override
	public Mono<PetDto> findOldest() {
		return petsRepository.findOldestPet()
						.switchIfEmpty(Mono.error(new NotFoundException("Pets collection is empty in database")))
						.map(petMapper::toDto);
	}

	@Override
	public Flux<PetDto> findAll() {
		return petsRepository.findAll().map(petMapper::toDto);
	}
}
