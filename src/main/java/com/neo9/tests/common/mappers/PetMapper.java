package com.neo9.tests.common.mappers;

import com.neo9.tests.common.entities.Pet;
import com.neo9.tests.common.models.PetDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PetMapper {
	PetMapper INSTANCE = Mappers.getMapper(PetMapper.class);

	PetDto toDto(Pet source);

	Pet toEntity(PetDto source);
}
