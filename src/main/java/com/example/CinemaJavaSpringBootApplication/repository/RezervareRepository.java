package com.example.CinemaJavaSpringBootApplication.repository;


import com.example.CinemaJavaSpringBootApplication.model.Rezervare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RezervareRepository extends JpaRepository<Rezervare, Long> {

    // Găsește toate rezervările după nume
    List<Rezervare> findByNume(String nume);

    // Calculează câte locuri sunt ocupate într-o sală
    @Query("SELECT COALESCE(SUM(r.locuri), 0) FROM Rezervare r WHERE r.nrSala = :nrSala")
    int getLocuriOcupate(int nrSala);
}
