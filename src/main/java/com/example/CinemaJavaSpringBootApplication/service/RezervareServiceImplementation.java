package com.example.CinemaJavaSpringBootApplication.service;

import com.example.CinemaJavaSpringBootApplication.model.Rezervare;
import com.example.CinemaJavaSpringBootApplication.repository.RezervareRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RezervareServiceImplementation implements RezervareService {

    private static final int CAPACITATE_MAX = 20;

    private final RezervareRepository rezervareRepository;

    // Injectare prin constructor - best practice
    public RezervareServiceImplementation(RezervareRepository rezervareRepository) {
        this.rezervareRepository = rezervareRepository;
    }

    @Override
    public void adaugaRezervare(Rezervare rezervare) {
        // Validare dată
        if (rezervare.getDataRezervare().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Data rezervării nu poate fi în trecut!");
        }

        // Validare capacitate
        int locuriOcupate = rezervareRepository.getLocuriOcupate(rezervare.getNrSala());
        if (locuriOcupate + rezervare.getLocuri() > CAPACITATE_MAX) {
            throw new IllegalStateException("Sală plină!");
        }

        rezervareRepository.save(rezervare);
    }

    @Override
    public List<Rezervare> getRezervariByNume(String nume) {
        return rezervareRepository.findByNume(nume);
    }

    @Override
    public int getLocuriLibere(int nrSala) {
        int locuriOcupate = rezervareRepository.getLocuriOcupate(nrSala);
        return CAPACITATE_MAX - locuriOcupate;
    }

    @Override
    public void stergeRezervare(Long id) {
        rezervareRepository.deleteById(id);
    }

    @Override
    public List<Rezervare> getAllRezervari() {
        return rezervareRepository.findAll();
    }
}