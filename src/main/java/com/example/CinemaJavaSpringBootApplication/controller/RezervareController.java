package com.example.CinemaJavaSpringBootApplication.controller;

import com.example.CinemaJavaSpringBootApplication.model.Rezervare;
import com.example.CinemaJavaSpringBootApplication.service.RezervareService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Arrays;

@Controller
public class RezervareController {

    private final RezervareService rezervareService;

    public RezervareController(RezervareService rezervareService) {
        this.rezervareService = rezervareService;
    }

    // ===================== DASHBOARD =====================
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (!isLoggedIn(session)) return "redirect:/login";
        return "dashboard";
    }

    // ===================== REZERVARE =====================
    @GetMapping("/rezervare")
    public String showRezervareForm(HttpSession session, Model model) {
        if (!isLoggedIn(session)) return "redirect:/login";

        model.addAttribute("rezervare", new Rezervare());
        model.addAttribute("filme", getFilme());
        model.addAttribute("sali", getSali());
        return "rezervare";
    }

    @PostMapping("/rezervare")
    public String adaugaRezervare(@ModelAttribute Rezervare rezervare,
                                  HttpSession session,
                                  Model model) {
        if (!isLoggedIn(session)) return "redirect:/login";

        try {
            rezervareService.adaugaRezervare(rezervare);
            model.addAttribute("success", "Rezervare adăugată cu succes!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
        }

        model.addAttribute("rezervare", new Rezervare());
        model.addAttribute("filme", getFilme());
        model.addAttribute("sali", getSali());
        return "rezervare";
    }

    // ===================== AFISARE REZERVARI =====================
    @GetMapping("/afisareRezervari")
    public String showAfisareForm(HttpSession session, Model model) {
        if (!isLoggedIn(session)) return "redirect:/login";
        return "afisareRezervari";
    }

    @PostMapping("/afisareRezervari")
    public String afisareRezervari(@RequestParam String nume,
                                   HttpSession session,
                                   Model model) {
        if (!isLoggedIn(session)) return "redirect:/login";

        List<Rezervare> rezervari = rezervareService.getRezervariByNume(nume);
        model.addAttribute("rezervari", rezervari);
        model.addAttribute("nume", nume);
        return "afisareRezervari";
    }

    // ===================== VERIFICARE CAPACITATE =====================
    @GetMapping("/verificareCapacitate")
    public String showVerificareForm(HttpSession session, Model model) {
        if (!isLoggedIn(session)) return "redirect:/login";

        model.addAttribute("sali", getSali());
        return "verificareCapacitate";
    }

    @PostMapping("/verificareCapacitate")
    public String verificareCapacitate(@RequestParam int nrSala,
                                       HttpSession session,
                                       Model model) {
        if (!isLoggedIn(session)) return "redirect:/login";

        int locuriLibere = rezervareService.getLocuriLibere(nrSala);
        model.addAttribute("locuriLibere", locuriLibere);
        model.addAttribute("nrSala", nrSala);
        model.addAttribute("sali", getSali());
        return "verificareCapacitate";
    }

    // ===================== STERGERE =====================
    @PostMapping("/stergeRezervare")
    public String stergeRezervare(@RequestParam Long id,
                                  HttpSession session,
                                  Model model) {
        if (!isLoggedIn(session)) return "redirect:/login";

        rezervareService.stergeRezervare(id);
        return "redirect:/afisareRezervari";
    }

    // ===================== HELPER METHODS =====================
    private boolean isLoggedIn(HttpSession session) {
        return Boolean.TRUE.equals(session.getAttribute("loggedIn"));
    }

    private List<String> getFilme() {
        return Arrays.asList(
                "Top Gun: Maverick",
                "Ratatouille",
                "Inception",
                "Avatar",
                "Interstellar"
        );
    }

    private List<Integer> getSali() {
        return Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
    }
}
