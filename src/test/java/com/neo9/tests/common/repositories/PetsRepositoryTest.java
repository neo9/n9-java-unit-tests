package com.neo9.tests.common.repositories;

import com.neo9.tests.common.entities.Pet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class PetsRepositoryTest {

	@Autowired
	private ReactiveMongoTemplate reactiveMongoTemplate;

	@Autowired
	private PetsRepository petsRepository;

	@AfterEach
	public void cleanAfterTest() {
		// clean all documents in database
		reactiveMongoTemplate.remove(new Query(), Pet.class);
	}

	@Test
	public void findOldestPet_ok() {
		// given
		reactiveMongoTemplate.insert(Pet.builder().age(24).build()).block();
		Pet oldest = reactiveMongoTemplate.insert(Pet.builder().age(47).build()).block();
		reactiveMongoTemplate.insert(Pet.builder().age(35).build()).block();
		// assert
		assertNotNull(oldest);
		StepVerifier.create(petsRepository.findOldestPet())
						.expectNextMatches(pet -> {
							assertEquals(oldest.getAge(), pet.getAge(), "Oldest pet should be 47 yo");
							return true;
						})
						.expectComplete()
						.verify();
	}

	@Test
	public void execute_asserts_after_stepVerifier_reactive() {
		// assert
		StepVerifier.create(
						petsRepository.insert(Pet.builder().name("médor").build())
						.then(reactiveMongoTemplate.find(Query.query(Criteria.where("name").is("médor")), Pet.class).single())
		)
						.expectNextMatches(pet -> {
							assertEquals("médor", pet.getName(), "Name should be equals");
							return true;
						})
						.expectComplete()
						.verify();
	}

	@Test
	public void execute_blocking_asserts() {
		// assert
		petsRepository.insert(Pet.builder().name("lutèce").build()).block();
		final Pet pet = reactiveMongoTemplate.find(Query.query(Criteria.where("name").is("lutèce")), Pet.class).single().block();
		assertNotNull(pet);
		assertEquals("lutèce", pet.getName(), "Age should be equals");
	}
}
