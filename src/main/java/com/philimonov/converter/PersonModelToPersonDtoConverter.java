package com.philimonov.converter;

import com.philimonov.dao.PersonModel;
import com.philimonov.service.PersonDTO;
import org.springframework.stereotype.Service;

@Service
public class PersonModelToPersonDtoConverter implements Converter<PersonModel, PersonDTO> {
    @Override
    public PersonDTO convert(PersonModel source) {
        PersonDTO personDto = new PersonDTO();
        personDto.setId(source.getId());
        personDto.setEmail(source.getEmail());

        return personDto;
    }
}
