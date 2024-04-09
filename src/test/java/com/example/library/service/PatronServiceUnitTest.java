package com.example.library.service;

import com.example.library.entity.Patron;
import com.example.library.exceptionHandling.exception.NotFoundException;
import com.example.library.repository.PatronRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class PatronServiceUnitTest {
    @MockBean
    private PatronRepository patronRepository;

    @Test
    void givenNoPatrons_whenFindAll_thenReturnEmptyList() {
        when(patronRepository.findAll()).thenReturn(new ArrayList<>());
        PatronService patronService = new PatronServiceImpl(patronRepository);

        List<?> patronsList = patronService.findAll();

        assertTrue(patronsList.isEmpty());
    }

    @Test
    void givenOnePatron_whenFindById_thenReturnPatron() {
        Patron expected = Patron.builder().
                id(1).
                firstName("John").
                lastName("Doe").
                email("johndoe@example.com").
                address("10 first street").
                build();
        when(patronRepository.findById(1)).thenReturn(Optional.of(expected));
        PatronService patronService = new PatronServiceImpl(patronRepository);

        Patron result = patronService.findById(1);

        assertEquals(expected, result);
    }

    @Test
    void givenNoPatrons_whenFindById_thenReturnNull() {
        when(patronRepository.findById(1)).thenReturn(Optional.empty());
        PatronService patronService = new PatronServiceImpl(patronRepository);

        Patron result = patronService.findById(1);

        assertNull(result);
    }

    @Test
    void givenNoPatrons_whenDeleteById_thenThrowNotFoundException() {
        when(patronRepository.existsById(1)).thenReturn(false);
        PatronService patronService = new PatronServiceImpl(patronRepository);

        assertThrows(NotFoundException.class, () -> patronService.deleteById(1));
    }

}
