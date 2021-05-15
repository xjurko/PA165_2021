from typing import List, Set, Tuple
from model import Movie, Actor, Director


def nullable(v):
  return v if v else "NULL"


def writeMovies(movies: List[Movie]):
  """
    create table Movie (
       id bigint generated by default as identity,
        caption varchar(255),
        externalRef varchar(255),
        name varchar(255) not null,
        releaseYear integer,
        runtimeMin integer not null,
        posterUrl varchar, //ADD to abckend
        primary key (id)
    )


    create table Movie_genres (
       Movie_id bigint not null,
        genres varchar(255)
    )
  """

  with open("./imdb/out/movies.sql", 'w') as file:
    for m in movies:
      file.write(
        f"INSERT INTO Movie (id, caption, externalRef, name, releaseYear, runtimeMin, posterUrl) VALUES ({m.id},'{m.caption}','{m.externalRef}','{m.title}',{m.year},{m.runtimeMin}, '{m.posterUrl}');\n")

  with open("./imdb/out/movies_genres.sql", 'w') as file:
    for m in movies:
      for g in m.genres:
        file.write(f"INSERT INTO Movie_genres (Movie_id, genres) VALUES ({m.id}, '{g}');\n")


def writeActors(actors: List[Actor]):
  """
    create table Actor (
       id bigint generated by default as identity,
        birthDate date,
        deathDate date,
        fullName varchar(255) not null,
        height integer not null, //remove this from backend
        externalRef varchar, //add this to backend
        posterUrl varchar //add this to backend
        primary key (id)
    )
  """

  with open("./imdb/out/actors.sql", 'w') as file:
    for a in actors:
      file.write(
        f"INSERT INTO Actor (id, birthDate, deathDate, fullName, extenralRef, posterUrl) VALUES ({a.id}, {nullable(a.birthYear)}, {nullable(a.deathYear)}, '{a.name}', '{a.externalRef}', '{a.posterUrl}');\n")


def writeDirectors(directors: List[Director]):
  """
    create table Director (
       id bigint generated by default as identity,
        birthDate date,
        name varchar(255) not null,
        externalRef varchar, //add this to backend
        posterUrl varchar //add this to backend
        primary key (id)
    )
  """
  with open("./imdb/out/directors.sql", 'w') as file:
    for d in directors:
      file.write(
        f"INSERT INTO Director (id, birthDate, deathDate, name, extenralRef, posterUrl) VALUES ({d.id}, {nullable(d.birthYear)}, {nullable(d.deathYear)}, '{d.name}', '{d.externalRef}', '{d.posterUrl}');\n")


def writeMovieActorMappings(mappings: Set[Tuple[str, str]]):
  """
    create table Actor_Movie (
        cast_id bigint not null,
        movies_id bigint not null,
        primary key (cast_id, movies_id)
    )
  """

  with open("./imdb/out/actor_movie.sql", 'w') as file:
    for m in mappings:
      file.write(f"INSERT INTO Actor_Movie (cast_id, movies_id) VALUES ({m[1]}, {m[0]});\n")


def writeMovieDirectorMappings(mappings: Set[Tuple[str, str]]):
  """
    create table Director_Movie (
       directors_id bigint not null,
       movies_id bigint not null,
       primary key (directors_id, movies_id)
    )
  """

  with open("./imdb/out/director_movie.sql", 'w') as file:
    for m in mappings:
      file.write(f"INSERT INTO Director_Movie (directors_id, movies_id) VALUES ({m[1]}, {m[0]});\n")
