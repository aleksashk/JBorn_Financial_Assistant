package com.philimonov.converter;

import com.philimonov.dao.PersonModel;
import com.philimonov.service.PersonDTO;
import org.springframework.stereotype.Service;

@Service
public class PersonModelToPersonDtoConverter implements Converter<PersonModel, PersonDTO> {
    @Override
    public PersonDTO convert(PersonModel source) {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(source.getId());
        personDTO.setEmail(source.getEmail());
        return personDTO;
    }
}
