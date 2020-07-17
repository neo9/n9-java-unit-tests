package com.neo9.tests.common.repositories.impl;

import com.neo9.tests.common.entities.Pet;
import com.neo9.tests.common.repositories.PetsRepositoryCustom;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Mono;

public class PetsRepositoryCustomImpl implements PetsRepositoryCustom {

	private final ReactiveMongoTemplate reactiveMongoTemplate;

	public PetsRepositoryCustomImpl(ReactiveMongoTemplate reactiveMongoTemplate) {
		this.reactiveMongoTemplate = reactiveMongoTemplate;
	}

	@Override
	public Mono<Pet> findOldestPet() {
		Query find = new Query().with(Sort.by(Sort.Direction.DESC, "age")).limit(1);
		return reactiveMongoTemplate.findOne(find, Pet.class);
	}
}
