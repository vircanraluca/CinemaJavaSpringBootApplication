package com.example.CinemaJavaSpringBootApplication.service;

import com.example.CinemaJavaSpringBootApplication.model.Reservation;

import java.util.List;

public interface ReservationService {

    void addReservation(Reservation reservation);

    List<Reservation> getReservationsByName(String name);

    int getAvailableSeats(int hallNumber);

    void deleteReservation(Long id);

    List<Reservation> getAllReservations();
}