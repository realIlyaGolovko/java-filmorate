CREATE TABLE IF NOT EXISTS users
(
    user_id  BIGSERIAL PRIMARY KEY,
    email    VARCHAR(255) NOT NULL UNIQUE,
    login    VARCHAR(255) NOT NULL UNIQUE,
    name     VARCHAR(255),
    birthday DATE,
    created  DATETIME DEFAULT NOW(),
    updated  DATETIME DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS friends
(
    user_id   BIGINT NOT NULL REFERENCES USERS (USER_ID),
    friend_id BIGINT NOT NULL REFERENCES USERS (USER_ID),
    CONSTRAINT pk_friends PRIMARY KEY (user_id, friend_id),
    CONSTRAINT check_self_friend CHECK (user_id != friend_id)
);

CREATE TABLE IF NOT exists genres
(
    genre_id BIGINT PRIMARY KEY,
    name     VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF not EXISTS mpa_ratings
(
    mpa_rating_id BIGINT PRIMARY KEY,
    name          VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS films
(
    film_id       BIGSERIAL PRIMARY KEY,
    name          VARCHAR(200) NOT NULL,
    description   VARCHAR(255),
    release_date  DATE,
    duration      INT4,
    mpa_rating_id BIGINT       NOT NULL REFERENCES mpa_ratings (mpa_rating_id),
    created       DATETIME DEFAULT NOW(),
    updated       DATETIME DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS film_genres
(
    film_id  BIGINT NOT NULL REFERENCES films (film_id),
    genre_id BIGINT NOT NULL REFERENCES genres (genre_id),
    CONSTRAINT pk_film_genres PRIMARY KEY (film_id, genre_id)
);

CREATE TABLE IF NOT EXISTS film_likes
(
    film_id BIGINT NOT NULL REFERENCES films (film_id),
    user_id BIGINT NOT NULL REFERENCES users (user_id),
    CONSTRAINT pk_likes PRIMARY key (film_id, user_id)
);
