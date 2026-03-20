package com.example.CinemaJavaSpringBootApplication.controller;

import com.example.CinemaJavaSpringBootApplication.model.Reservation;
import com.example.CinemaJavaSpringBootApplication.repository.ReservationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Controller
public class PaymentController {

    private final ReservationRepository reservationRepository;

    public PaymentController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/payment/{id}")
    public String showPayment(@PathVariable Long id,
                              @RequestParam(required = false, defaultValue = "STANDARD") String ticketType,
                              Model model) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        double price = calculatePrice(ticketType, reservation.getReservationDate());

        model.addAttribute("reservation", reservation);
        model.addAttribute("ticketType", ticketType);
        model.addAttribute("price", price);
        return "payment";
    }

    @PostMapping("/payment/{id}")
    public String processPayment(@PathVariable Long id,
                                 @RequestParam String ticketType,
                                 @RequestParam String cardNumber,
                                 @RequestParam String cardHolder,
                                 @RequestParam String expiry,
                                 @RequestParam String cvv,
                                 Model model) {

        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        double price = calculatePrice(ticketType, reservation.getReservationDate());

        reservation.setTicketType(ticketType);
        reservation.setPrice(price);
        reservation.setPaid(true);
        reservationRepository.save(reservation);

        return "redirect:/confirmation?movie=" + reservation.getMovie()
                + "&date=" + reservation.getReservationDate()
                + "&seat=" + reservation.getSeatLabel()
                + "&price=" + price
                + "&ticketType=" + ticketType;
    }

    private double calculatePrice(String ticketType, LocalDate date) {
        double base = ticketType.equals("VIP") ? 55.0 : 35.0;
        DayOfWeek day = date.getDayOfWeek();
        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
            base += 10.0;
        }
        return base;
    }
}