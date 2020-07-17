package com.neo9.tests.integrations;

import com.neo9.tests.common.entities.Pet;
import com.neo9.tests.common.models.PetDto;
import com.neo9.tests.exceptions.models.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureWebTestClient
public class PetsControllerIT {
	@Autowired
	private WebTestClient webTestClient;
	@Autowired
	private ReactiveMongoTemplate reactiveMongoTemplate;

	@BeforeEach
	public void cleanDb() {
		reactiveMongoTemplate.remove(new Query(), "pets").block();
	}

	@Test
	public void findOldestPet_IT_OK() {
		// given
		reactiveMongoTemplate.insert(Pet.builder().owner("owner").name("name").age(11).build()).block();
		reactiveMongoTemplate.insert(Pet.builder().owner("owner2").name("name2").age(24).build()).block();
		reactiveMongoTemplate.insert(Pet.builder().owner("owner3").name("name3").age(17).build()).block();
		// assert
		webTestClient.get()
						.uri("/pets/find_oldest")
						.exchange()
						.expectStatus().isOk()
						.expectBody(PetDto.class)
						.value(res -> {
							assertEquals(24, res.getAge());
							assertEquals("owner2", res.getOwner());
							assertEquals("name2", res.getName());
						});
	}

	@Test
	public void findOldestPet_IT_not_found() {
		// assert
		webTestClient.get()
						.uri("/pets/find_oldest")
						.exchange()
						.expectStatus().isNotFound()
						.expectBody(ApiError.class)
						.value(res -> {
							log.info("ApiError received : {}", res);
							assertEquals("Pets collection is empty in database", res.getMessage());
						});
	}
}
