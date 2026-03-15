package com.example.CinemaJavaSpringBootApplication.model;


import jakarta.persistence.*;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titlu;

    @Column(length = 1000)
    private String descriere;

    private String imagine; // URL imagine

    private Integer durata; // minute

    private String gen; // Action, Comedy, etc.

    private Integer an;

    public Movie() {}

    public Movie(String titlu, String descriere, String imagine, Integer durata, String gen, Integer an) {
        this.titlu = titlu;
        this.descriere = descriere;
        this.imagine = imagine;
        this.durata = durata;
        this.gen = gen;
        this.an = an;
    }

    // Getteri și Setteri
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitlu() { return titlu; }
    public void setTitlu(String titlu) { this.titlu = titlu; }

    public String getDescriere() { return descriere; }
    public void setDescriere(String descriere) { this.descriere = descriere; }

    public String getImagine() { return imagine; }
    public void setImagine(String imagine) { this.imagine = imagine; }

    public Integer getDurata() { return durata; }
    public void setDurata(Integer durata) { this.durata = durata; }

    public String getGen() { return gen; }
    public void setGen(String gen) { this.gen = gen; }

    public Integer getAn() { return an; }
    public void setAn(Integer an) { this.an = an; }
}