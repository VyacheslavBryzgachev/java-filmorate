create table IF NOT EXISTS Genre (
                       id INTEGER,
                       name VARCHAR(50),
                    CONSTRAINT Pk_genres PRIMARY KEY (id),
                    CONSTRAINT Uc_genres_name_id UNIQUE (name)
);
create table IF NOT EXISTS Mpa (
                     id INTEGER not null ,
                     name VARCHAR(20) not null ,
                     CONSTRAINT Pk_mpa PRIMARY KEY (id),
                     CONSTRAINT Ua_mpa_name UNIQUE (name)
);
create table IF NOT EXISTS Films  (
       id INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
       name VARCHAR(50) not null,
       description VARCHAR(255) not null,
       release_date DATETIME not null,
       duration INTEGER null,
       mpa INTEGER null,
       CONSTRAINT Pk_films PRIMARY KEY (id)
);
create table IF NOT EXISTS Users (
        id INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
       email VARCHAR(50) not null ,
       login VARCHAR(50) not null ,
       name VARCHAR(50),
       birthday DATETIME,
        CONSTRAINT PK_USERS PRIMARY KEY (id),
        CONSTRAINT UC_USERS_EMAIL UNIQUE (email),
        CONSTRAINT UC_USERS_LOGIN UNIQUE (login)
);
CREATE TABLE IF NOT EXISTS User_friends (
     user_id INTEGER,
     friend_id INTEGER
);
CREATE TABLE IF NOT EXISTS Film_likes (
        user_id INTEGER,
        film_id INTEGER
);

CREATE TABLE IF NOT EXISTS Film_genres
(
    film_id INTEGER,
    genre_id INTEGER,
    CONSTRAINT Pk_film_genres PRIMARY KEY (film_id, genre_id)
);


