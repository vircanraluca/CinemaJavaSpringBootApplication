package com.example.CinemaJavaSpringBootApplication.model;

import jakarta.persistence.*;

@Entity
@Table(name = "halls")
public class Hall {

    @Id
    private Integer hallNumber;

    @Column(nullable = false)
    private Integer capacity;

    private String currentMovie;

    public Hall() {}

    public Hall(Integer hallNumber, Integer capacity, String currentMovie) {
        this.hallNumber = hallNumber;
        this.capacity = capacity;
        this.currentMovie = currentMovie;
    }

    public Integer getHallNumber() { return hallNumber; }
    public void setHallNumber(Integer hallNumber) { this.hallNumber = hallNumber; }

    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }

    public String getCurrentMovie() { return currentMovie; }
    public void setCurrentMovie(String currentMovie) { this.currentMovie = currentMovie; }
}