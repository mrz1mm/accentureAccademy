package com.corsojava.esercizi.jpapresenze.michael.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Presenza {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    private Long idStudente;
    private LocalDate data;
    @Enumerated(EnumType.STRING)
    private StatoPresenza stato;
    private String nota;

    public Presenza() {
        super();
    }

    public Presenza(Long idStudente, LocalDate data, StatoPresenza stato, String nota) {
        super();
        this.idStudente = idStudente;
        this.data = data;
        this.stato = stato;
        this.nota = nota;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdStudente() { return idStudente; }
    public void setIdStudente(Long idStudente) { this.idStudente = idStudente; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public StatoPresenza getStato() { return stato; }
    public void setStato(StatoPresenza stato) { this.stato = stato; }

    public String getNota() { return nota; }
    public void setNota(String nota) { this.nota = nota; }

    @Override
    public String toString() {
        return "Presenza [id=" + id + ", idStudente=" + idStudente + ", data=" + data + ", stato=" + stato + "]";
    }

}
