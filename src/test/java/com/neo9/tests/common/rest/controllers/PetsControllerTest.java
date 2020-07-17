package com.neo9.tests.common.rest.controllers;

import com.neo9.tests.common.models.PetDto;
import com.neo9.tests.common.services.impl.PetsServiceImpl;
import com.neo9.tests.exceptions.NotFoundException;
import com.neo9.tests.exceptions.models.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureWebTestClient
public class PetsControllerTest {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private PetsServiceImpl petsService;

	@Test
	@DisplayName("findOldestPet with no pets in database")
	public void findOldestPet_empty_db() {
		// given
		doReturn(Mono.error(new NotFoundException("Some error message"))).when(petsService).findOldest();
		// assert
		webTestClient.get()
						.uri("/pets/find_oldest")
						.exchange()
						.expectStatus().isNotFound()
						.expectBody(ApiError.class)
						.value(res -> {
							log.info("ApiError received : {}", res);
							assertEquals("Some error message", res.getMessage());
						});
	}

	@Test
	@DisplayName("findOldestPet should return 42 years old pet")
	public void findOldestPet_ok() {
		// given
		PetDto mockedOldestPet = PetDto.builder().owner("owner").name("pet very old").age(42).id("#42").build();
		doReturn(Mono.just(mockedOldestPet)).when(petsService).findOldest();
		// assert
		webTestClient.get()
						.uri("/pets/find_oldest")
						.exchange()
						.expectStatus().isOk()
						.expectBody(PetDto.class)
						.value(res -> {
							log.info("Oldest pet received : {}", res);
							assertEquals(42, res.getAge(), "Age should be 42 yo");
							assertEquals("owner", res.getOwner());
							assertEquals("pet very old", res.getName());
						});
	}

	@Test
	@DisplayName("insert should return 42 years old pet")
	public void insert_ok() {
		// given
		PetDto petToSave = PetDto.builder().owner("owner").name("pet very old").age(42).build();
		PetDto mockedPet = PetDto.builder().owner("owner").name("pet very old").age(42).id("#42").build();
		doReturn(Mono.just(mockedPet)).when(petsService).insert(refEq(petToSave, "id"));

		// assert
		webTestClient.post()
						.uri("/pets")
						.bodyValue(petToSave)
						.exchange()
						.expectStatus().isOk()
						.expectBody(PetDto.class)
						.value(res -> {
							log.info("Saved pet received : {}", res);
							assertEquals(42, res.getAge(), "Age should be 42 yo");
							assertEquals("owner", res.getOwner());
							assertEquals("pet very old", res.getName());
						});
	}

	@Test
	@DisplayName("findAll should return 3 pets")
	public void findAll_3_pets_ok() {
		// given
		PetDto mockedPet = PetDto.builder().owner("owner1").name("name1").age(5).id("#41").build();
		PetDto mockedPet2 = PetDto.builder().owner("owner2").name("name2").age(15).id("#42").build();
		PetDto mockedPet3 = PetDto.builder().owner("owner3").name("name3").age(10).id("#43").build();
		doReturn(Flux.just(mockedPet, mockedPet2, mockedPet3)).when(petsService).findAll();
		String expectedRes = "[{\"id\":\"#41\",\"name\":\"name1\",\"owner\":\"owner1\",\"age\":5},{\"id\":\"#42\",\"name\":\"name2\",\"owner\":\"owner2\",\"age\":15},{\"id\":\"#43\",\"name\":\"name3\",\"owner\":\"owner3\",\"age\":10}]";
		// assert
		webTestClient.get()
						.uri("/pets")
						.exchange()
						.expectStatus().isOk()
						.expectBody()
						.json(expectedRes);
	}
}
