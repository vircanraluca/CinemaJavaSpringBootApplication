package com.example.CinemaJavaSpringBootApplication.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "hall_showtimes")
public class HallShowtime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hall_number", nullable = false)
    private Integer hallNumber;

    @Column(name = "show_time", nullable = false)
    private String showTime;

    @Column(name = "movie")
    private String movie;

    @Column(name = "show_date")
    private LocalDate showDate;

    public HallShowtime() {}

    public HallShowtime(Integer hallNumber, String showTime, String movie, LocalDate showDate) {
        this.hallNumber = hallNumber;
        this.showTime = showTime;
        this.movie = movie;
        this.showDate = showDate;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getHallNumber() { return hallNumber; }
    public void setHallNumber(Integer hallNumber) { this.hallNumber = hallNumber; }
    public String getShowTime() { return showTime; }
    public void setShowTime(String showTime) { this.showTime = showTime; }
    public String getMovie() { return movie; }
    public void setMovie(String movie) { this.movie = movie; }
    public LocalDate getShowDate() { return showDate; }
    public void setShowDate(LocalDate showDate) { this.showDate = showDate; }
}