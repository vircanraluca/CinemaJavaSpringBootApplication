package com.example.CinemaJavaSpringBootApplication.controller;

import com.example.CinemaJavaSpringBootApplication.model.HallShowtime;
import com.example.CinemaJavaSpringBootApplication.model.Movie;
import com.example.CinemaJavaSpringBootApplication.repository.HallShowtimeRepository;
import com.example.CinemaJavaSpringBootApplication.repository.MovieRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ProgramController {

    private final MovieRepository movieRepository;
    private final HallShowtimeRepository hallShowtimeRepository;

    public ProgramController(MovieRepository movieRepository,
                             HallShowtimeRepository hallShowtimeRepository) {
        this.movieRepository = movieRepository;
        this.hallShowtimeRepository = hallShowtimeRepository;
    }

    @GetMapping("/program")
    public String showProgram(
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String title,
            Model model) {

        String genreTrimmed = (genre != null && !genre.isBlank()) ? genre : null;
        String titleTrimmed = (title != null && !title.isBlank()) ? title : null;

        List<Movie> movies = movieRepository.findByFilters(titleTrimmed, genreTrimmed);

        List<String> genres = movieRepository.findAll()
                .stream().map(Movie::getGenre).distinct().sorted()
                .collect(Collectors.toList());

        // Pentru fiecare film, gasim showtimes din BD
        Map<String, List<ShowtimeOption>> movieShowtimes = new LinkedHashMap<>();

        for (Movie m : movies) {
            List<HallShowtime> showtimes = hallShowtimeRepository
                    .findByMovieAndShowDateGreaterThanEqual(m.getTitle(), LocalDate.now());
            if (!showtimes.isEmpty()) {
                movieShowtimes.put(m.getTitle(), showtimes.stream()
                        .map(st -> new ShowtimeOption(
                                st.getHallNumber(),
                                st.getShowDate() + " " + st.getShowTime(),
                                st.getId()))
                        .collect(Collectors.toList()));
            }
        }

        model.addAttribute("movies", movies);
        model.addAttribute("genres", genres);
        model.addAttribute("selectedGenre", genre);
        model.addAttribute("selectedTitle", title);
        model.addAttribute("movieShowtimes", movieShowtimes);
        return "program";
    }

    public static class ShowtimeOption {
        public Integer hallNumber;
        public String showTime;
        public Long showtimeId;

        public ShowtimeOption(Integer hallNumber, String showTime, Long showtimeId) {
            this.hallNumber = hallNumber;
            this.showTime = showTime;
            this.showtimeId = showtimeId;
        }
    }
}