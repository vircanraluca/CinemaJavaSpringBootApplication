package com.example.CinemaJavaSpringBootApplication.service;

import com.example.CinemaJavaSpringBootApplication.model.Reservation;
import com.example.CinemaJavaSpringBootApplication.repository.ReservationRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationServiceImplementation implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final HallService hallService;

    public ReservationServiceImplementation(ReservationRepository reservationRepository,
                                            HallService hallService) {
        this.reservationRepository = reservationRepository;
        this.hallService = hallService;
    }

    @Override
    public void addReservation(Reservation reservation) {
        saveAndReturn(reservation);
    }


    @Override
    public Reservation saveAndReturn(Reservation reservation) {
        if (reservation.getReservationDate() == null) {
            reservation.setReservationDate(LocalDate.now());
        }
        if (reservation.getReservationDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Reservation date cannot be in the past!");
        }
        int capacity = hallService.getCapacity(reservation.getHallNumber());
        int occupiedSeats = reservationRepository.getOccupiedSeatsByShowtime(reservation.getShowtimeId());
        if (occupiedSeats + reservation.getSeats() > capacity) {
            throw new IllegalStateException("Hall is full!");
        }

        // Salveaza userul logat
        String loggedUser = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        reservation.setUsername(loggedUser);

        return reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> getReservationsByName(String name) {
        return reservationRepository.findByName(name);
    }

    @Override
    public int getAvailableSeats(int hallNumber) {
        int capacity = hallService.getCapacity(hallNumber);
        int occupiedSeats = reservationRepository.getOccupiedSeats(hallNumber);
        return capacity - occupiedSeats;
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