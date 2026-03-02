package com.example.CinemaJavaSpringBootApplication.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "rezervari")
public class Rezervare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String film;

    @Column(name = "nr_sala", nullable = false)
    private Integer nrSala;

    @Column(nullable = false)
    private String nume;

    @Column(nullable = false)
    private Integer locuri;

    @Column(name = "data_rezervare", nullable = false)
    private LocalDate dataRezervare;

    // Constructor gol - necesar pentru JPA
    public Rezervare() {}

    // Constructor cu parametri

    public Rezervare(String film, Integer nrSala, String nume, Integer locuri, LocalDate dataRezervare) {
        this.film = film;
        this.nrSala = nrSala;
        this.nume = nume;
        this.locuri = locuri;
        this.dataRezervare = dataRezervare;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilm() {
        return film;
    }

    public void setFilm(String film) {
        this.film = film;
    }

    public Integer getNrSala() {
        return nrSala;
    }

    public void setNrSala(Integer nrSala) {
        this.nrSala = nrSala;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Integer getLocuri() {
        return locuri;
    }

    public void setLocuri(Integer locuri) {
        this.locuri = locuri;
    }

    public LocalDate getDataRezervare() {
        return dataRezervare;
    }

    public void setDataRezervare(LocalDate dataRezervare) {
        this.dataRezervare = dataRezervare;
    }
}