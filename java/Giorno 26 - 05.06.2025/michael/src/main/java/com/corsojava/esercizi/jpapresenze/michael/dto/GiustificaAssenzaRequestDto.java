package com.corsojava.esercizi.jpapresenze.michael.dto;

import java.time.LocalDate;

public class GiustificaAssenzaRequestDto {

    private Long idStudente;
    private LocalDate data;
    private String nota;

    public Long getIdStudente() { return idStudente; }
    public void setIdStudente(Long idStudente) { this.idStudente = idStudente; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public String getNota() { return nota; }
    public void setNota(String nota) { this.nota = nota; }

}
