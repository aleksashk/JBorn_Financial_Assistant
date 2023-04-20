package com.philimonov.converter;

import com.philimonov.dao.PersonModel;
import com.philimonov.service.PersonDto;

public class PersonModelToPersonDtoConverter implements Converter<PersonModel, PersonDto> {
    @Override
    public PersonDto convert(PersonModel source) {
        PersonDto personDto = new PersonDto();
        personDto.setId(source.getId());
        personDto.setEmail(source.getEmail());

        return personDto;
    }
}
