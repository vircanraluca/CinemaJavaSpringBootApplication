package com.example.CinemaJavaSpringBootApplication.controller;

import com.example.CinemaJavaSpringBootApplication.repository.RezervareRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SeatsController {

    private final RezervareRepository rezervareRepository;

    public SeatsController(RezervareRepository rezervareRepository) {
        this.rezervareRepository = rezervareRepository;
    }

    @GetMapping("/seats")
    public String showSeats(
            @RequestParam String film,
            Model model) {

        // Generează sala goală - scaunele ocupate se vor actualiza după ce userul alege sala
        List<Rand> randuri = genereazaRanduri(0);

        model.addAttribute("film", film);
        model.addAttribute("randuri", randuri);
        return "seats";
    }

    private List<Rand> genereazaRanduri(int locuriOcupate) {
        String[] litere = {"A", "B", "C", "D", "E"};
        int locuriPerRand = 8;
        List<Rand> randuri = new ArrayList<>();
        int contor = 0;

        for (String litera : litere) {
            Rand rand = new Rand(litera);
            for (int i = 1; i <= locuriPerRand; i++) {
                contor++;
                rand.adaugaLoc(new Loc(i, contor <= locuriOcupate));
            }
            randuri.add(rand);
        }
        return randuri;
    }

    // Clase interne pentru structura sălii
    public static class Rand {
        private String litera;
        private List<Loc> locuri = new ArrayList<>();

        public Rand(String litera) { this.litera = litera; }
        public void adaugaLoc(Loc loc) { locuri.add(loc); }
        public String getLitera() { return litera; }
        public List<Loc> getLocuri() { return locuri; }
    }

    public static class Loc {
        private int numar;
        private boolean ocupat;

        public Loc(int numar, boolean ocupat) {
            this.numar = numar;
            this.ocupat = ocupat;
        }
        public int getNumar() { return numar; }
        public boolean isOcupat() { return ocupat; }
    }
}