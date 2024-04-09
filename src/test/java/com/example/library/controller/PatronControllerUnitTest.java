package com.example.library.controller;

import com.example.library.entity.Patron;
import com.example.library.exceptionHandling.exception.NotFoundException;
import com.example.library.service.PatronService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class PatronControllerUnitTest {

    @MockBean
    private PatronService patronService;

    @Test
    void givenNoPatrons_whenFindAll_thenReturnEmptyList() {
        when(patronService.findAll()).thenReturn(new ArrayList<>());
        PatronController patronController = new PatronController(patronService);

        ResponseEntity<?> response = patronController.getAll();

        assertEquals(response.getBody(),new ArrayList<>());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void givenOnePatron_whenFindById_thenReturnOnePatron() {
        Patron patron = Patron.builder().
                id(1).
                firstName("John").
                lastName("Doe").
                email("johndoe@example.com").
                address("10 first street").
                build();

        when(patronService.findById(1)).thenReturn(patron);
        PatronController patronController = new PatronController(patronService);

        ResponseEntity<?> response = patronController.getById(1);
        assertEquals(response.getBody(),patron);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void givenNoPatrons_whenFindById_thenThrowNotFoundException() {
        when(patronService.findById(any())).thenReturn(null);
        PatronController patronController = new PatronController(patronService);

        assertThrows(NotFoundException.class,
                ()-> patronController.getById(1));
    }
}
