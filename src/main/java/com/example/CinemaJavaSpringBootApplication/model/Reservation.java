package com.example.CinemaJavaSpringBootApplication.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String movie;

    @Column(name = "hall_number", nullable = false)
    private Integer hallNumber;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer seats;

    @Column(name = "reservation_date", nullable = false)
    private LocalDate reservationDate;

    public Reservation() {}

    public Reservation(String movie, Integer hallNumber, String name, Integer seats, LocalDate reservationDate) {
        this.movie = movie;
        this.hallNumber = hallNumber;
        this.name = name;
        this.seats = seats;
        this.reservationDate = reservationDate;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMovie() { return movie; }
    public void setMovie(String movie) { this.movie = movie; }

    public Integer getHallNumber() { return hallNumber; }
    public void setHallNumber(Integer hallNumber) { this.hallNumber = hallNumber; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getSeats() { return seats; }
    public void setSeats(Integer seats) { this.seats = seats; }

    public LocalDate getReservationDate() { return reservationDate; }
    public void setReservationDate(LocalDate reservationDate) { this.reservationDate = reservationDate; }
}