package com.corsojava.esercizi.jpapresenze.michael.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Studente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cognome;

    public Studente() {
        super();
    }

    public Studente(String nome, String cognome) {
        super();
        this.nome = nome;
        this.cognome = cognome;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }

    @Override
    public String toString() {
        return "Studente [id=" + id + ", nome=" + nome + ", cognome=" + cognome + "]";
    }

}
