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
    private int nrSala;

    @Column(nullable = false)
    private String nume;

    @Column(nullable = false)
    private int locuri;

    @Column(name = "data_rezervare", nullable = false)
    private LocalDate dataRezervare;

    // Constructor gol - necesar pentru JPA
    public Rezervare() {}

    // Constructor cu parametri
    public Rezervare(String film, int nrSala, String nume, int locuri, LocalDate dataRezervare) {
        this.film = film;
        this.nrSala = nrSala;
        this.nume = nume;
        this.locuri = locuri;
        this.dataRezervare = dataRezervare;
    }

    // Getteri
    public Long getId()                  { return id; }
    public String getFilm()              { return film; }
    public int getNrSala()               { return nrSala; }
    public String getNume()              { return nume; }
    public int getLocuri()               { return locuri; }
    public LocalDate getDataRezervare()  { return dataRezervare; }

    // Setteri
    public void setId(Long id)                       { this.id = id; }
    public void setFilm(String film)                 { this.film = film; }
    public void setNrSala(int nrSala)                { this.nrSala = nrSala; }
    public void setNume(String nume)                 { this.nume = nume; }
    public void setLocuri(int locuri)                { this.locuri = locuri; }
    public void setDataRezervare(LocalDate data)     { this.dataRezervare = data; }
}