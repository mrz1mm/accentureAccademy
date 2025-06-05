package com.corsojava.esercizi.jpapresenze.michael.dto;

import java.time.LocalDate;

import com.corsojava.esercizi.jpapresenze.michael.model.StatoPresenza;

public class PresenzaRequestDto {

    private Long idStudente;
    private LocalDate data;
    private StatoPresenza stato;
    private String nota;

    public Long getIdStudente() { return idStudente; }
    public void setIdStudente(Long idStudente) { this.idStudente = idStudente; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public StatoPresenza getStato() { return stato; }
    public void setStato(StatoPresenza stato) { this.stato = stato; }

    public String getNota() { return nota; }
    public void setNota(String nota) { this.nota = nota; }

}
