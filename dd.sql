CREATE TABLE Movie (
    tmdb_id INT PRIMARY KEY,
    adult boolean,
    budget BIGINT,
    homepage VARCHAR(255),
    imdb_id INT,
    original_language VARCHAR(255),
    original_title VARCHAR(255),
    overview TEXT,
    popularity FLOAT,
    poster_path VARCHAR(255),
    release_date DATE,
    revenue BIGINT,
    runtime INT,
    status VARCHAR(255),
    tagline TEXT,
    title VARCHAR(255),
    video BOOLEAN,
    vote_average FLOAT,
    vote_count INT
);

CREATE TABLE Collection (
    id INT PRIMARY KEY,
    name VARCHAR(255),
    poster_path VARCHAR(255),
    backdrop_path VARCHAR(255)
);

CREATE TABLE Genre (
    id INT PRIMARY KEY,
    name VARCHAR(255)
);

ALTER TABLE Movie
ADD COLUMN collection_id INT,
ADD CONSTRAINT fk_collection_id
    FOREIGN KEY (collection_id)
    REFERENCES Collection(id);

CREATE TABLE MovieToGenre (
    tmdb_id INT,
    genre_id INT,
    PRIMARY KEY (tmdb_id, genre_id),
    FOREIGN KEY (tmdb_id) REFERENCES Movie(tmdb_id),
    FOREIGN KEY (genre_id) REFERENCES Genre(id)
);


CREATE TABLE SpokenLanguage (
    id CHAR(2) PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE ProductionCountry (
    iso_3166_1 CHAR(2) PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE ProductionCompany (
    id INT PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE MovieToSpokenLanguage (
    tmdb_id INT,
    spoken_language_id CHAR(2),
    PRIMARY KEY (tmdb_id, spoken_language_id),
    FOREIGN KEY (tmdb_id) REFERENCES Movie(tmdb_id),
    FOREIGN KEY (spoken_language_id) REFERENCES SpokenLanguage(id)
);

CREATE TABLE MovieToProductionCountry (
    tmdb_id INT,
    production_country_iso CHAR(2),
    PRIMARY KEY (tmdb_id, production_country_iso),
    FOREIGN KEY (tmdb_id) REFERENCES Movie(tmdb_id),
    FOREIGN KEY (production_country_iso) REFERENCES ProductionCountry(iso_3166_1)
);

CREATE TABLE MovieToProductionCompany (
    tmdb_id INT,
    production_company_id INT,
    PRIMARY KEY (tmdb_id, production_company_id),
    FOREIGN KEY (tmdb_id) REFERENCES Movie(tmdb_id),
    FOREIGN KEY (production_company_id) REFERENCES ProductionCompany(id)
);

CREATE TABLE Credit (
    credit_id VARCHAR(255) PRIMARY KEY,
    gender INT,
    name VARCHAR(255),
    profile_path VARCHAR(255),
    department VARCHAR(255),
    job VARCHAR(255),
    tmdb_id INT,
    FOREIGN KEY (tmdb_id) REFERENCES Movie(tmdb_id)
);

CREATE TABLE Cast (
    cast_id INT PRIMARY KEY,
    `character` VARCHAR(255),
    gender INT,
    credit_id VARCHAR(255),
    name VARCHAR(255),
    profile_path VARCHAR(255),
    tmdb_id INT,
    `order` INT,
    FOREIGN KEY (tmdb_id) REFERENCES Movie(tmdb_id),
    FOREIGN KEY (credit_id) REFERENCES Credit(credit_id)
);

CREATE TABLE Keyword (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE Keyword_Movie (
    keyword_id INT,
    tmdb_id INT,
    FOREIGN KEY (keyword_id) REFERENCES Keyword(id),
    FOREIGN KEY (tmdb_id) REFERENCES Movie(tmdb_id)
);

CREATE TABLE MovieIds (
    movie_id INT PRIMARY KEY,
    imdb_id INT,
    tmdb_id INT,
    FOREIGN KEY (tmdb_id) REFERENCES Movie(tmdb_id)
);

CREATE TABLE UserMovieRatings (
    user_id INT,
    movie_id INT,
    rating FLOAT,
    `timestamp` TIMESTAMP,
    FOREIGN KEY (movie_id) REFERENCES MovieIds(movie_id)
);



