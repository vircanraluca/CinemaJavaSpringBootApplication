package com.example.CinemaJavaSpringBootApplication.repository;

import com.example.CinemaJavaSpringBootApplication.model.Rezervare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.CinemaJavaSpringBootApplication.model.Rezervare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RezervareRepository extends JpaRepository<Rezervare, Long> {

    // Existente
    List<Rezervare> findByNume(String nume);

    @Query("SELECT COALESCE(SUM(r.locuri), 0) FROM Rezervare r WHERE r.nrSala = :nrSala")
    int getLocuriOcupate(int nrSala);

    // Noi - pentru pagina de program
    List<Rezervare> findByFilm(String film);
    List<Rezervare> findByDataRezervare(LocalDate data);
    List<Rezervare> findByFilmAndDataRezervare(String film, LocalDate data);
}