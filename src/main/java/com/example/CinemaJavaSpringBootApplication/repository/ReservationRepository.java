package com.example.CinemaJavaSpringBootApplication.repository;

import com.example.CinemaJavaSpringBootApplication.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByName(String name);

    @Query("SELECT COALESCE(SUM(r.seats), 0) FROM Reservation r WHERE r.hallNumber = :hallNumber")
    int getOccupiedSeats(int hallNumber);

    List<Reservation> findByMovie(String movie);
    List<Reservation> findByReservationDate(LocalDate date);
    List<Reservation> findByMovieAndReservationDate(String movie, LocalDate date);
}