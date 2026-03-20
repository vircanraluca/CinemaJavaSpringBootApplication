package com.example.CinemaJavaSpringBootApplication.repository;

import com.example.CinemaJavaSpringBootApplication.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByName(String name);

    @Query("SELECT COALESCE(SUM(r.seats), 0) FROM Reservation r WHERE r.hallNumber = :hallNumber")
    int getOccupiedSeats(int hallNumber);

    @Query("SELECT COALESCE(SUM(r.seats), 0) FROM Reservation r WHERE r.showtimeId = :showtimeId")
    int getOccupiedSeatsByShowtime(@Param("showtimeId") Long showtimeId);

    @Query("SELECT r.seatLabel FROM Reservation r WHERE r.showtimeId = :showtimeId AND r.seatLabel IS NOT NULL")
    List<String> getOccupiedSeatLabelsByShowtime(@Param("showtimeId") Long showtimeId);

    List<Reservation> findByMovie(String movie);
    List<Reservation> findByReservationDate(LocalDate date);
    List<Reservation> findByMovieAndReservationDate(String movie, LocalDate date);

    @Query("SELECT r FROM Reservation r WHERE " +
            "(:name IS NULL OR LOWER(r.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:movie IS NULL OR LOWER(r.movie) LIKE LOWER(CONCAT('%', :movie, '%'))) AND " +
            "(:hallNumber IS NULL OR r.hallNumber = :hallNumber) AND " +
            "(:date IS NULL OR r.reservationDate = :date)")
    List<Reservation> findByFilters(
            @Param("name") String name,
            @Param("movie") String movie,
            @Param("hallNumber") Integer hallNumber,
            @Param("date") LocalDate date);

    List<Reservation> findByUsername(String username);
}