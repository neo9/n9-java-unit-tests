package com.neo9.tests.common.rest.controllers;

import com.neo9.tests.common.models.PetDto;
import com.neo9.tests.common.services.PetsService;
import com.neo9.tests.exceptions.NotFoundException;
import com.neo9.tests.exceptions.models.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
public class PetsController {

	private final PetsService petsService;

	public PetsController(PetsService petsService) {
		this.petsService = petsService;
	}

	@GetMapping("pets")
	public Flux<PetDto> findAllPets() {
		return petsService.findAll();
	}

	@GetMapping("pets/find_oldest")
	public Mono<PetDto> findOldestPet() {
		return petsService.findOldest();
	}

	@PostMapping("pets")
	public Mono<PetDto> saveNewPet(@RequestBody PetDto petDto) {
		return petsService.insert(petDto);
	}

	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Mono<ApiError> handleExceptions(NotFoundException nfe) {
		return Mono.just(
						ApiError.builder()
										.status(nfe.getHttpStatus())
										.message(nfe.getMessage())
										.timestamp(LocalDateTime.now())
										.build()
		);
	}
}
