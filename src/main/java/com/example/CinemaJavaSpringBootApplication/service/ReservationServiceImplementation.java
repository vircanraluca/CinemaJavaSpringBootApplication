package com.example.CinemaJavaSpringBootApplication.service;

import com.example.CinemaJavaSpringBootApplication.model.Reservation;
import com.example.CinemaJavaSpringBootApplication.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationServiceImplementation implements ReservationService {

    private static final int MAX_CAPACITY = 20;

    private final ReservationRepository reservationRepository;

    public ReservationServiceImplementation(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public void addReservation(Reservation reservation) {
        if (reservation.getReservationDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Reservation date cannot be in the past!");
        }

        int occupiedSeats = reservationRepository.getOccupiedSeats(reservation.getHallNumber());
        if (occupiedSeats + reservation.getSeats() > MAX_CAPACITY) {
            throw new IllegalStateException("Hall is full!");
        }

        reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> getReservationsByName(String name) {
        return reservationRepository.findByName(name);
    }

    @Override
    public int getAvailableSeats(int hallNumber) {
        int occupiedSeats = reservationRepository.getOccupiedSeats(hallNumber);
        return MAX_CAPACITY - occupiedSeats;
    }

    @Override
    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }
}