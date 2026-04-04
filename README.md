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

<img width="1885" height="944" alt="image" src="https://github.com/user-attachments/assets/4f67bfa7-de8f-4375-9914-5fdace9e2615" />
<img width="1881" height="945" alt="image" src="https://github.com/user-attachments/assets/91255d3b-ccc6-41ae-bed4-5303aed77bdf" />
<img width="1875" height="777" alt="image" src="https://github.com/user-attachments/assets/1e289998-24ca-4547-8953-2d3aae6f5e1b" />
<img width="1892" height="932" alt="image" src="https://github.com/user-attachments/assets/4c088341-3de5-4c19-ab32-2fa6ee629ecc" />
<img width="1904" height="942" alt="image" src="https://github.com/user-attachments/assets/fb53ca98-b0ea-4c7f-863b-a9d3213bb2dd" />
<img width="1912" height="917" alt="image" src="https://github.com/user-attachments/assets/71d2381d-02f8-4a26-bc43-660709a808df" />
<img width="1892" height="952" alt="image" src="https://github.com/user-attachments/assets/c03be8f2-3ea2-4124-ba1a-49bbe0f99bd7" />
<img width="1902" height="958" alt="image" src="https://github.com/user-attachments/assets/bf665e84-4bae-4b4f-b0bc-bd5ef244b75d" />
<img width="1885" height="945" alt="image" src="https://github.com/user-attachments/assets/e6a3d6f4-71b5-48ab-bcee-600198774b9a" />
<img width="1902" height="992" alt="image" src="https://github.com/user-attachments/assets/9ba34edb-a495-4ae4-aee8-e1d8755c44ec" />
<img width="1890" height="937" alt="image" src="https://github.com/user-attachments/assets/ec44d9bf-16c5-4d36-a7b5-f173fb575f36" />











