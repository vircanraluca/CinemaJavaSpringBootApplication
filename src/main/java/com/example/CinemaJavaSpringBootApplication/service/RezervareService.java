package com.example.CinemaJavaSpringBootApplication.service;

import com.example.CinemaJavaSpringBootApplication.model.Rezervare;

import java.util.List;

public interface RezervareService {

    void adaugaRezervare(Rezervare rezervare);

    List<Rezervare> getRezervariByNume(String nume);

    int getLocuriLibere(int nrSala);

    void stergeRezervare(Long id);

    List<Rezervare> getAllRezervari();
}