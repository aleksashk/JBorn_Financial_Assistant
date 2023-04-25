package com.philimonov.service;

import com.philimonov.converter.PersonModelToPersonDtoConverter;
import com.philimonov.dao.PersonDao;
import com.philimonov.dao.PersonModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthServiceTest {
    @InjectMocks
    AuthService authService;
    @Mock
    PersonDao personDao;
    @Mock
    DigestService digestService;
    @Mock
    PersonModelToPersonDtoConverter personDtoConverter;

    int id = 1;
    String email = "aleks@gmail.com";
    String password = "password";
    String hash = "hex";

    @Test
    public void authFailed() {
        when(digestService.hex(password)).thenReturn(hash);
        when(personDao.findByEmailAndHash(email, hash)).thenReturn(null);

        PersonDTO personDTO = authService.auth(email, hash);
        assertNull(personDTO);

        verify(digestService, times(1)).hex(password);
        verify(personDao, times(1)).findByEmailAndHash(email, hash);
        verifyZeroInteractions(personDtoConverter);
    }

    @Test

    public void authSuccess() {
        when(digestService.hex(password)).thenReturn(hash);
        PersonModel personModel = new PersonModel();
        personModel.setId(id);
        personModel.setEmail(email);
        personModel.setPassword(password);

        when(personDao.findByEmailAndHash(email, hash)).thenReturn(personModel);

        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(id);
        personDTO.setEmail(email);
        when(personDtoConverter.convert(personModel)).thenReturn(personDTO);

        PersonDTO person = authService.auth(email, password);
        assertNotNull(person);
        assertEquals(personDTO, person);
        verify(digestService, times(1)).hex(password);
        verify(personDao, times(1)).findByEmailAndHash(email, hash);
        verify(personDtoConverter, times(1)).convert(personModel);
    }

    @Test
    public void registration() {
        when(digestService.hex(password)).thenReturn(hash);
        PersonModel personModel = new PersonModel();
        personModel.setId(id);
        personModel.setEmail(email);
        personModel.setPassword(hash);
        when(personDao.insert(email, hash)).thenReturn(personModel);

        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(id);
        personDTO.setEmail(email);
        when(personDtoConverter.convert(personModel)).thenReturn(personDTO);

        PersonDTO person = authService.registration(email, password);
        assertNotNull(person);
        assertEquals(personDTO, person);
        verify(digestService, times(1)).hex(password);
        verify(personDao, times(1)).insert(email, password);
        verify(personDtoConverter, times(1)).convert(personModel);
    }
}