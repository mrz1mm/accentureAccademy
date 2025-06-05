package com.corsojava.esercizi.jpapresenze.michael.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.corsojava.esercizi.jpapresenze.michael.model.Studente;
import com.corsojava.esercizi.jpapresenze.michael.service.StudenteService;

@RestController
@RequestMapping("/api/studenti")
public class StudenteController {

    private final StudenteService studenteService;

    public StudenteController(StudenteService studenteService) {
        this.studenteService = studenteService;
    }

    @GetMapping("/{id}")
    public Studente getStudenteById(@PathVariable Long id) {
        return studenteService.getStudenteById(id);
    }

    @GetMapping
    public List<Studente> getAllStudenti() {
        return studenteService.getAllStudenti();
    }

    @PostMapping
    public Studente creaStudente(@RequestBody Studente studente) {
        return studenteService.creaStudente(studente);
    }

}
