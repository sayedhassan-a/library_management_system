package com.example.library.controller;

import com.example.library.dto.PatronDTO;
import com.example.library.entity.Patron;
import com.example.library.service.PatronService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patrons")
@RequiredArgsConstructor
public class PatronController {
    private final PatronService patronService;
    @GetMapping("")
    public ResponseEntity<List<Patron>> getAll() {
        return ResponseEntity.ok(patronService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Patron> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(patronService.findById(id));
    }
    @PostMapping("")
    public ResponseEntity<Patron> create(@Valid @RequestBody PatronDTO patronDTO) {
        Patron patron = Patron.builder().
                firstName(patronDTO.getFirstName()).
                lastName(patronDTO.getLastName()).
                email(patronDTO.getEmail()).
                address(patronDTO.getAddress()).
                build();
        return ResponseEntity.ok(patronService.save(patron));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Patron> update(@PathVariable Integer id,
                                         @Valid @RequestBody PatronDTO patronDTO) {
        Patron patron = Patron.builder().
                id(id).
                firstName(patronDTO.getFirstName()).
                lastName(patronDTO.getLastName()).
                email(patronDTO.getEmail()).
                address(patronDTO.getAddress()).
                build();

        return ResponseEntity.ok(patronService.save(patron));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        patronService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
