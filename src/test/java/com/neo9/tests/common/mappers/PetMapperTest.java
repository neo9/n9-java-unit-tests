package com.neo9.tests.common.mappers;

import com.neo9.tests.common.entities.Pet;
import com.neo9.tests.common.models.PetDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PetMapperTest {

	@Test
	public void toEntity_ok() {
		// given
		PetDto petDto = PetDto.builder()
						.id("id#42")
						.name("name")
						.owner("owner")
						.age(42)
						.build();
		// then
		Pet mapped = PetMapper.INSTANCE.toEntity(petDto);
		// assert
		assertNotNull(mapped);
		assertEquals("id#42", mapped.getId(), "id should be equals");
		assertEquals("name", mapped.getName(), "name should be equals");
		assertEquals("owner", mapped.getOwner(), "owner should be equals");
		assertEquals(42, mapped.getAge(), "age should be equals");
	}

	@Test
	public void toDto_ok() {
		// given
		Pet pet = Pet.builder()
						.id("id#42")
						.name("name")
						.owner("owner")
						.age(42)
						.build();
		// then
		PetDto mapped = PetMapper.INSTANCE.toDto(pet);
		// assert
		assertNotNull(mapped);
		assertEquals("id#42", mapped.getId(), "id should be equals");
		assertEquals("name", mapped.getName(), "name should be equals");
		assertEquals("owner", mapped.getOwner(), "owner should be equals");
		assertEquals(42, mapped.getAge(), "age should be equals");
	}
}
