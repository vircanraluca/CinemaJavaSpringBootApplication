package com.example.CinemaJavaSpringBootApplication.model;

import jakarta.persistence.*;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(name = "image_url")
    private String image;

    @Column(name = "duration_minutes")
    private Integer duration;

    @Column(name = "genre_type")
    private String genre;

    @Column(name = "release_year")
    private Integer year;

    public Movie() {}

    public Movie(String title, String description, String image, Integer duration, String genre, Integer year) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.duration = duration;
        this.genre = genre;
        this.year = year;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
}