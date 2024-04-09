package com.example.library.service;

import com.example.library.entity.Patron;
import com.example.library.exceptionHandling.exception.NotFoundException;
import com.example.library.repository.PatronRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatronServiceImpl implements PatronService{
    private final PatronRepository patronRepository;
    @Override
    public Patron findById(Integer id) {
        return patronRepository.findById(id).orElse(null);
    }

    @Override
    public List<Patron> findAll() {
        return patronRepository.findAll();
    }

    @Override
    public Patron save(Patron patron) {
        return patronRepository.save(patron);
    }

    @Override
    public void deleteById(Integer id) {
        if(patronRepository.existsById(id)) {
            patronRepository.deleteById(id);
        }
        else{
            throw new NotFoundException("Patron with id:" + id + " does not " +
                    "exist");
        }
    }
}
