🎬 Cinema Java Spring Boot Application
A full-stack cinema reservation web application built with Java Spring Boot, Thymeleaf, Spring Security, and H2 Database.

📋 Table of Contents

Overview
Features
Tech Stack
Project Structure
Getting Started
Default Credentials
User Roles & Routes
Database


Overview
Cinema App is a web-based ticket reservation system that allows users to browse movies, select showtimes, choose specific seats, and complete a simulated payment. Administrators can manage movies, halls, showtimes, and view all reservations with statistics.

Features
👤 Regular User

Browse the movie schedule with filters (title, genre)
View available showtimes per movie (hall + date + time)
Select a specific seat from an interactive seat map
Complete a reservation with simulated card payment (Standard / VIP ticket)
View and cancel personal reservations (/my-reservations)

🔐 Admin

Manage movies — add, edit, delete
Manage halls — set capacity, manage showtimes per hall and date
View all reservations with filters (name, movie, hall, date)
Delete any reservation
View statistics — reservations per day, hall occupancy, top movies (Chart.js)
Export all reservations to a .txt file


Tech Stack
LayerTechnologyBackendJava 21, Spring Boot 4.xSecuritySpring Security, BCryptFrontendThymeleaf, HTML, CSSDatabaseH2 (file-based persistence)ORMSpring Data JPA / HibernateChartsChart.js (CDN)BuildMaven
