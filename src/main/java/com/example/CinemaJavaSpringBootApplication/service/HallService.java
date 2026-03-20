package com.example.CinemaJavaSpringBootApplication.service;

import com.example.CinemaJavaSpringBootApplication.model.Hall;
import com.example.CinemaJavaSpringBootApplication.model.HallShowtime;
import com.example.CinemaJavaSpringBootApplication.repository.HallRepository;
import com.example.CinemaJavaSpringBootApplication.repository.HallShowtimeRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class HallService {

    private static final int DEFAULT_CAPACITY = 20;
    private final HallRepository hallRepository;
    private final HallShowtimeRepository hallShowtimeRepository;

    public HallService(HallRepository hallRepository,
                       HallShowtimeRepository hallShowtimeRepository) {
        this.hallRepository = hallRepository;
        this.hallShowtimeRepository = hallShowtimeRepository;
    }

    @PostConstruct
    public void initHalls() {
        for (int i = 1; i <= 9; i++) {
            if (!hallRepository.existsById(i)) {
                hallRepository.save(new Hall(i, DEFAULT_CAPACITY, null));
            }
        }
    }

    public List<Hall> getAllHalls() { return hallRepository.findAll(); }

    public Hall getHall(Integer hallNumber) {
        return hallRepository.findById(hallNumber).orElseGet(() -> {
            Hall h = new Hall(hallNumber, DEFAULT_CAPACITY, null);
            return hallRepository.save(h);
        });
    }

    public int getCapacity(Integer hallNumber) {
        return getHall(hallNumber).getCapacity();
    }

    public void saveHall(Hall hall) { hallRepository.save(hall); }

    public List<HallShowtime> getShowtimes(Integer hallNumber) {
        return hallShowtimeRepository.findByHallNumberOrderByShowTimeAsc(hallNumber);
    }

    public void addShowtime(Integer hallNumber, String showTime, String movie, LocalDate showDate) {
        hallShowtimeRepository.save(new HallShowtime(hallNumber, showTime, movie, showDate));
    }

    public void deleteShowtime(Long id) {
        hallShowtimeRepository.deleteById(id);
    }
}