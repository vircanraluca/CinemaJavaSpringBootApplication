package com.example.CinemaJavaSpringBootApplication.repository;

import com.example.CinemaJavaSpringBootApplication.model.HallShowtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HallShowtimeRepository extends JpaRepository<HallShowtime, Long> {
    List<HallShowtime> findByHallNumberOrderByShowTimeAsc(Integer hallNumber);
    List<HallShowtime> findByMovieAndShowDateGreaterThanEqual(String movie, LocalDate date);
    List<HallShowtime> findByHallNumberAndShowDate(Integer hallNumber, LocalDate date);
    void deleteByHallNumber(Integer hallNumber);
}