package com.example.CinemaJavaSpringBootApplication.repository;

import com.example.CinemaJavaSpringBootApplication.model.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HallRepository extends JpaRepository<Hall, Integer> {}