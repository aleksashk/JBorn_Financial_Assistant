package com.philimonov.service;

import com.philimonov.converter.Converter;
import com.philimonov.dao.PersonDao;
import com.philimonov.dao.PersonModel;

public class AuthService {
    private final PersonDao personDao;
    private final DigestService digestService;
    private final Converter<PersonModel, PersonDTO> personDtoConverter;

    public AuthService(PersonDao personDao, DigestService digestService, Converter<PersonModel, PersonDTO> personDtoConverter) {
        this.personDao = personDao;
        this.digestService = digestService;
        this.personDtoConverter = personDtoConverter;
    }

    public PersonDTO auth(String email, String password) {
        String hash = digestService.hex(password);
        PersonModel personModel = personDao.findByEmailAndHash(email, hash);
        if (personModel == null) {
            return null;
        }
        return personDtoConverter.convert(personModel);
    }

    public PersonDTO registration(String email, String password) {
        String hash = digestService.hex(password);
        PersonModel personModel = personDao.insert(email, hash);
        if (personModel == null) {
            return null;
        }
        return personDtoConverter.convert(personModel);
    }
}











