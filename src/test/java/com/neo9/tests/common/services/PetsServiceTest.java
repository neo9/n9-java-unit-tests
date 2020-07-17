package com.neo9.tests.common.services;

import com.neo9.tests.common.entities.Pet;
import com.neo9.tests.common.mappers.PetMapperImpl;
import com.neo9.tests.common.models.PetDto;
import com.neo9.tests.common.repositories.PetsRepository;
import com.neo9.tests.common.services.impl.PetsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class PetsServiceTest {

	@Mock
	private PetsRepository petsRepository;

	@Mock
	private PetMapperImpl petMapper;

	@InjectMocks
	private PetsServiceImpl petsService;

	@Test
	public void insert_pet() {
		// given
		final Pet savedPet = Pet.builder().age(42).name("pet").owner("owner").id("id#42").build();
		final PetDto petDtoToSave = PetDto.builder().age(42).name("pet").owner("owner").build();
		doReturn(Mono.just(savedPet)).when(petsRepository).save(any(Pet.class));
		doReturn(savedPet).when(petMapper).toEntity(any(PetDto.class));
		doReturn(petDtoToSave).when(petMapper).toDto(any(Pet.class));
		// assert
		StepVerifier.create(petsService.insert(petDtoToSave))
						.expectNextMatches(petDto -> {
							assertEquals(42, petDto.getAge(), "Age should be equals");
							assertEquals("owner", petDto.getOwner(), "Owner should be equals");
							assertEquals("pet", petDto.getName(), "Name should be equals");
							return true;
						})
						.expectComplete()
						.verify();
	}

	@Test
	public void findOldest_pet() {
		// given
		final Pet savedPet = Pet.builder().age(42).name("pet").owner("owner").id("id#42").build();
		final PetDto petDtoToSave = PetDto.builder().age(42).name("pet").owner("owner").build();
		doReturn(Mono.just(savedPet)).when(petsRepository).findOldestPet();
		doReturn(petDtoToSave).when(petMapper).toDto(any(Pet.class));
		// assert
		StepVerifier.create(petsService.findOldest())
						.expectNextMatches(petDto -> {
							assertEquals(42, petDto.getAge(), "Age should be equals");
							assertEquals("owner", petDto.getOwner(), "Owner should be equals");
							assertEquals("pet", petDto.getName(), "Name should be equals");
							return true;
						})
						.expectComplete()
						.verify();
	}
}
