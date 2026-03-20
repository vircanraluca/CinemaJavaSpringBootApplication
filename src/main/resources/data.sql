DELETE FROM reservations;
DELETE FROM hall_showtimes;
DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM movies;

INSERT INTO movies (id, title, description, image_url, duration_minutes, genre_type, release_year) VALUES
(1, 'The Dark Knight', 'When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.', 'https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg', 152, 'Action', 2008),
(2, 'Inception', 'A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.', 'https://image.tmdb.org/t/p/w500/oYuLEt3zVCKq57qu2F8dT7NIa6f.jpg', 148, 'Sci-Fi', 2010),
(3, 'Interstellar', 'A team of explorers travel through a wormhole in space in an attempt to ensure humanity survival.', 'https://image.tmdb.org/t/p/w500/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg', 169, 'Sci-Fi', 2014),
(4, 'The Godfather', 'The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.', 'https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_SX300.jpg', 175, 'Crime', 1972),
(5, 'Pulp Fiction', 'The lives of two mob hitmen, a boxer, a gangster and his wife intertwine in four tales of violence and redemption.', 'https://image.tmdb.org/t/p/w500/d5iIlFn5s0ImszYzBPb8JPIfbXD.jpg', 154, 'Crime', 1994),
(6, 'The Shawshank Redemption', 'Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.', 'https://image.tmdb.org/t/p/w500/lyQBXzOQSuE59IsHyhrp0qIiPAz.jpg', 142, 'Drama', 1994),
(7, 'Forrest Gump', 'The presidencies of Kennedy and Johnson, the Vietnam War, the Watergate scandal and other historical events unfold from the perspective of an Alabama man with an IQ of 75.', 'https://image.tmdb.org/t/p/w500/arw2vcBveWOVZr6pxd9XTd1TdQa.jpg', 142, 'Drama', 1994),
(8, 'The Matrix', 'A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.', 'https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg', 136, 'Sci-Fi', 1999),
(9, 'Gladiator', 'A former Roman General sets out to exact vengeance against the corrupt emperor who murdered his family and sent him into slavery.', 'https://image.tmdb.org/t/p/w500/ty8TGRuvJLPUmAR1H1nRIsgwvim.jpg', 155, 'Action', 2000),
(10, 'The Lion King', 'Lion prince Simba and his father are targeted by his bitter uncle, who wants to ascend the throne himself.', 'https://image.tmdb.org/t/p/w500/sKCr78MXSLixwmZ8DyJLrpMsd15.jpg', 88, 'Animation', 1994),
(11, 'Titanic', 'A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.', 'https://image.tmdb.org/t/p/w500/9xjZS2rlVxm8SFx8kPC3aIGCOYQ.jpg', 194, 'Romance', 1997),
(12, 'Joker', 'A mentally troubled stand-up comedian embarks on a downward spiral that leads to the creation of an iconic villain.', 'https://image.tmdb.org/t/p/w500/udDclJoHjfjb8Ekgsd4FDteOkCU.jpg', 122, 'Drama', 2019);

INSERT INTO users (id, email, password, username) VALUES
(1, 'admin@gmail.com', '$2a$10$DXNRkgZwIJACfJAn7ZGT0.gbR6BS8u2tNwJNiFFJZqHPrTFpoMRbi', 'personalCinematograf');

INSERT INTO user_roles (user_id, role) VALUES
(1, 'ROLE_ADMIN');

INSERT INTO hall_showtimes (id, hall_number, movie, show_date, show_time) VALUES
(1, 1, 'The Dark Knight', '2026-03-25', '16:00'),
(2, 1, 'The Dark Knight', '2026-03-25', '18:00'),
(3, 1, 'The Dark Knight', '2026-03-25', '20:00'),
(4, 1, 'Inception', '2026-03-26', '17:00'),
(5, 1, 'Inception', '2026-03-26', '20:00'),
(6, 2, 'Gladiator', '2026-03-25', '16:00'),
(7, 2, 'Gladiator', '2026-03-25', '19:00'),
(8, 2, 'Interstellar', '2026-03-26', '18:00'),
(9, 3, 'The Matrix', '2026-03-25', '17:00'),
(10, 3, 'The Matrix', '2026-03-26', '20:00');

INSERT INTO reservations (id, hall_number, movie, name, reservation_date, seats, showtime_id, seat_label) VALUES
(1, 1, 'The Dark Knight', 'Raluca Vircan', '2026-03-25', 1, 1, 'A1'),
(2, 2, 'Gladiator', 'Florentina Geanina', '2026-03-25', 1, 6, 'A2'),
(3, 1, 'The Dark Knight', 'Melinda Zaria', '2026-03-25', 1, 1, 'A3');