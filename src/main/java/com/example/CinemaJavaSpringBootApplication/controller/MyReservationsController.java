package com.example.CinemaJavaSpringBootApplication.controller;

import com.example.CinemaJavaSpringBootApplication.model.Reservation;
import com.example.CinemaJavaSpringBootApplication.repository.ReservationRepository;
import com.example.CinemaJavaSpringBootApplication.service.ReservationService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MyReservationsController {

    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;

    public MyReservationsController(ReservationRepository reservationRepository,
                                    ReservationService reservationService) {
        this.reservationRepository = reservationRepository;
        this.reservationService = reservationService;
    }

    @GetMapping("/my-reservations")
    public String myReservations(Authentication authentication, Model model) {
        String username = authentication.getName();
        List<Reservation> reservations = reservationRepository.findByUsername(username);
        model.addAttribute("reservations", reservations);
        return "my-reservations";
    }

    @PostMapping("/my-reservations/cancel/{id}")
    public String cancelReservation(@RequestParam Long id,
                                    Authentication authentication) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        // Doar userul care a facut rezervarea poate anula
        if (reservation.getUsername().equals(authentication.getName())) {
            reservationService.deleteReservation(id);
        }

        return "redirect:/my-reservations";
    }
}