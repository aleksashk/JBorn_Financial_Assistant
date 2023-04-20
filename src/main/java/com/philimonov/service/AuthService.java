package com.philimonov.service;

import com.philimonov.converter.PersonModelToPersonDtoConverter;
import com.philimonov.dao.PersonDao;
import com.philimonov.dao.PersonModel;

public class AuthService {
    private final PersonDao personDao;
    private final DigestService digestService;
    private final PersonModelToPersonDtoConverter personDtoConverter;

    public AuthService() {
        this.personDao = new PersonDao();
        this.digestService = new Md5DigestService();
        this.personDtoConverter = new PersonModelToPersonDtoConverter();
    }

    public PersonDto auth(String email, String password) {
        String hash = digestService.hex(password);
        PersonModel personModel = personDao.findByEmailAndHash(email, hash);
        if (personModel == null) {
            return null;
        }
        return personDtoConverter.convert(personModel);
    }

    public PersonDto registration(String email, String password) {
        String hash = digestService.hex(password);
        PersonModel personModel = personDao.insert(email, hash);
        if (personModel == null) {
            return null;
        }
        return personDtoConverter.convert(personModel);
    }
}











