package com.corsojava.esercizi.jpapresenze.michael.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.corsojava.esercizi.jpapresenze.michael.model.Studente;

@Repository
public interface StudenteRepository extends JpaRepository<Studente, Long> {

}
