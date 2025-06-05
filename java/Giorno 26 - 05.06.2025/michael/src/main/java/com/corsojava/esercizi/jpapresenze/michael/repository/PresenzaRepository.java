package com.corsojava.esercizi.jpapresenze.michael.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.corsojava.esercizi.jpapresenze.michael.model.Presenza;
import com.corsojava.esercizi.jpapresenze.michael.model.StatoPresenza;

@Repository
public interface PresenzaRepository extends JpaRepository<Presenza, Long> {
    
    // Metodo per contare le assenze totali di uno studente in un determinato periodo.
    List<Presenza> findByIdStudenteAndDataBetweenAndStato(
        Long idStudente, LocalDate dataInizio, LocalDate dataFine, StatoPresenza stato
    );

    // Metodo ausiliario per trovare tutte le assenze in un periodo (per poi raggruppare per studente nel service)
    List<Presenza> findByDataBetweenAndStato(
        LocalDate dataInizio, LocalDate dataFine, StatoPresenza stato
    );

    // Metodo per trovare una specifica presenza di uno studente in una data (per giustificarla)
    List<Presenza> findByIdStudenteAndData(Long idStudente, LocalDate data);

}
