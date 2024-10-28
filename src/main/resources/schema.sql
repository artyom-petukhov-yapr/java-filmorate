-- schema.sql

DROP TABLE IF EXISTS film_genre;
DROP TABLE IF EXISTS film_like;

DROP TABLE IF EXISTS friendship;
DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS film;
DROP TABLE IF EXISTS genre;
DROP TABLE IF EXISTS mpa;

CREATE TABLE IF NOT EXISTS account
(
    id    SERIAL PRIMARY KEY,
    name  VARCHAR(50)  NOT NULL,
    login VARCHAR(50)  NOT NULL,
    email VARCHAR(100) NOT NULL,
    birthday DATE
);

CREATE INDEX IF NOT EXISTS idx_account_name ON account (name);
CREATE UNIQUE INDEX IF NOT EXISTS idx_account_login ON account (login);
CREATE UNIQUE INDEX IF NOT EXISTS idx_account_email ON account (email);

CREATE TABLE IF NOT EXISTS mpa
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS film
(
    id           SERIAL PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    release_date DATE,
    duration     INTEGER      NOT NULL,
    description  TEXT,
    mpa_id       INTEGER NULL REFERENCES mpa (id)
);

CREATE INDEX IF NOT EXISTS  idx_film_name ON film (name);

CREATE TABLE IF NOT EXISTS film_like
(
    account_id INTEGER NOT NULL,
    film_id   INTEGER NOT NULL,
    PRIMARY KEY (account_id, film_id),
    FOREIGN KEY (account_id) REFERENCES account (id),
    FOREIGN KEY (film_id) REFERENCES film (id)
);

CREATE TABLE IF NOT EXISTS friendship
(
    account_id INTEGER NOT NULL,
    friend_id  INTEGER NOT NULL,
    PRIMARY KEY (account_id, friend_id),
    FOREIGN KEY (account_id) REFERENCES account (id),
    FOREIGN KEY (friend_id) REFERENCES account (id)
);

CREATE TABLE IF NOT EXISTS genre
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS film_genre
(
    film_id INTEGER NOT NULL,
    genre_id INTEGER NOT NULL,
    PRIMARY KEY (film_id, genre_id),
    FOREIGN KEY (film_id) REFERENCES film (id),
    FOREIGN KEY (genre_id) REFERENCES genre (id)
);
