package com.corsojava.esercizi.jpapresenze.michael.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.corsojava.esercizi.jpapresenze.michael.model.Studente;
import com.corsojava.esercizi.jpapresenze.michael.repository.StudenteRepository;

@Service
public class StudenteService {

    private final StudenteRepository studenteRepository;

    public StudenteService(StudenteRepository studenteRepository) {
        this.studenteRepository = studenteRepository;
    }

    public Studente getStudenteById(Long id) {
        return studenteRepository.findById(id).orElse(null);
    }

    public List<Studente> getAllStudenti() {
        return studenteRepository.findAll();
    }

    public boolean esisteStudente(Long id) {
        return studenteRepository.existsById(id);
    }

    @Transactional
    public Studente creaStudente(Studente studente) {
        return studenteRepository.save(studente);
    }

    @Transactional
    public Studente updateStudente(Long id, Studente studenteDetails) {
        Studente studente = studenteRepository.findById(id).orElse(null);
        if (studente != null) {
            studente.setNome(studenteDetails.getNome());
            studente.setCognome(studenteDetails.getCognome());
            return studenteRepository.save(studente);
        }
        return null;
    }

    @Transactional
    public boolean deleteStudente(Long id) {
        if (studenteRepository.existsById(id)) {
            studenteRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
