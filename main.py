import csv
from concurrent.futures.thread import ThreadPoolExecutor
from dataclasses import dataclass
from typing import List, Optional
from bs4 import BeautifulSoup as Soup

import requests


@dataclass
class Movie:
  id: int
  title: str
  year: int
  caption: Optional[str]
  genres: List[str]
  externalRef: str
  posterUrl: Optional[str]


@dataclass
class Actor:
  id: int
  externalRef: str
  birthYear: int
  name: str
  posterUrl: str


@dataclass
class Director:
  id: int
  externalRef: str
  name: str
  yearofbirth: int
  posterUrl: str


def getImdbPhotosUrls(ids):
  buffer = dict()
  with ThreadPoolExecutor(80) as pool:
    while ids:
      pool.submit(fetchImdbPosterUrl, ids.pop(), buffer)

  return buffer


def fetchImdbPosterUrl(id, buffer):
  print(f"fetching actor {id}")
  resp = requests.get(f"https://www.imdb.com/name/{id}/")
  if resp.ok:
    html = resp.content
    soup = Soup(html, "lxml")
    imgtag = soup.find("img", {"id": "name-poster"})
    buffer[id] = imgtag["src"]
    return imgtag["src"]


def getMovieRatings():
  with open("./imdb/csv/title_ratings.csv", newline='') as csvfile:
    csv_reader = csv.reader(csvfile, delimiter=',')
    next(csv_reader)

    return {r[0]: r for r in csv_reader}


def getMovies():
  with open("./imdb/csv/title_basics.csv", newline='') as csvfile:
    csv_reader = csv.reader(csvfile, delimiter=',')
    next(csv_reader)

    return {x[0]: x for x in csv_reader if x[1] == "movie"}


def getPrincipals2():
  with open("./imdb/csv/name_basics.csv", newline='') as csvfile:
    csv_reader = csv.reader(csvfile, delimiter=',')
    next(csv_reader)

    return {x[0]: x[:4] for x in csv_reader}


def getPrincipals(filterIds):
  with open("./imdb/csv/name_basics.csv", newline='') as csvfile:
    lines = csvfile.readlines()
    csv_reader = csv.reader(lines)
    next(csv_reader)

    return {x[0]: x for x in csv_reader if x[0] in filterIds}


def getMoviePrincipalMappings(filter):
  with open("./imdb/csv/title_principal.csv", newline='') as csvfile:
    csv_reader = csv.reader(csvfile, delimiter=',')
    next(csv_reader)

    return {(x[0], x[1], x[3]) for x in csv_reader if x[0] in filter and int(x[3]) in {7, 8, 2}}


def createMovie(csvMovie, omdbMovie) -> Movie:
  normalizedUrl = urlNormalize(300, omdbMovie[0])
  return Movie(int(csvMovie[0][2:]), csvMovie[2], csvMovie[5], caption=omdbMovie[1], posterUrl=normalizedUrl,
               genres=csvMovie[8:], externalRef=csvMovie[0])


def createActor(csvActor, poster) -> Actor:
  return Actor(int(csvActor[0][2:]), csvActor[0], csvActor[2], csvActor[1], poster)


def getOmdbMovies(ids):
  buffer = dict()
  with ThreadPoolExecutor(80) as pool:
    while ids:
      pool.submit(fetchOmbdMovie, ids.pop(), buffer)

  return buffer


def fetchOmbdMovie(id, buffer):
  print(f"fetching movie {id}")
  resp = requests.get(f"https://www.imdb.com/title/{id}/")

  if resp.ok:
    html = resp.content
    soup = Soup(html, "lxml")
    imgtag = soup.select("div.poster > a > img", limit=1)
    plotTag = soup.select("div.plot_summary > div.summary_text", limit=1)

    if imgtag and plotTag:
      imgPlot = (imgtag[0]["src"], plotTag[0].text.strip())
      buffer[id] = imgPlot
      return imgPlot
    else:
      return "", ""


if __name__ == '__main__':
  movies = getMovies()
  print("moveis loaded")

  ratings = getMovieRatings()
  print("ratings loaded")

  topMovies = list(filter(lambda x: int(ratings.get(x[0], [0, 0, 0])[2]) > 50000, movies.items()))
  topMovies.sort(key=lambda x: float(ratings.get(x[0], [0, 0])[1]), reverse=True)
  topMovies = topMovies[:500]
  topMoviesIds = {m[0] for m in topMovies}

  moviePrincipalMappings = getMoviePrincipalMappings(topMoviesIds)
  movieActorsMappings = {(x[0], x[1]) for x in moviePrincipalMappings if int(x[2]) in {7, 8}}
  print("mappins1 loaded")

  movieDirectorMappings = {(x[0], x[1]) for x in moviePrincipalMappings if int(x[2]) == 2}
  print("mappins2 loaded")

  #
  filteredMovieActorMappings = {mapping for mapping in movieActorsMappings if mapping[0] in topMoviesIds}
  filteredMovieDirectorMappings = {mapping for mapping in movieDirectorMappings if mapping[0] in topMoviesIds}

  topMoviesActorIds = {mapping[1] for mapping in filteredMovieActorMappings}
  topMoviesDirectorIds = {mapping[1] for mapping in filteredMovieDirectorMappings}

  principals = getPrincipals(topMoviesActorIds.union(topMoviesDirectorIds))
  print("principals basics loaded")

  actors = [a[1] for a in principals.items() if a[0] in topMoviesActorIds]
  directors = [d[1] for d in principals.items() if d[0] in topMoviesDirectorIds]

  print(f"going to fetch {len(topMoviesDirectorIds.union(topMoviesActorIds))} photos")
  principalsPosters = getImdbPhotosUrls(list(topMoviesDirectorIds.union(topMoviesActorIds)).copy())

  decoratedActors = [createActor(a, principalsPosters.get(a[0], "")) for a in actors]
  # decoratedDirectors = [createDirector(a, principalsPosters.get(a[0], "")) for a in actors]

  for a in decoratedActors:
    print(a)

  omdbMovies = getOmdbMovies(topMoviesIds.copy())

  decoratedMovies = [createMovie(movie[1], omdbMovies.get(movie[0])) for movie in topMovies if movie[0] in omdbMovies]
  for m in decoratedMovies:
    print(m)

  print(f"total of {len(decoratedMovies)} movies  with {len(decoratedActors)} actors ")

  #
  #
  #
  #
  #
  #
  #
  #
  #
  #
  #
  #
  #
  #
  #
  #
  #
  #
  #
  #
  #
  #     create table Actor (
  #        id bigint generated by default as identity,
  #         birthDate date,
  #         deathDate date,
  #         fullName varchar(255) not null,
  #         height integer not null,
  #         primary key (id)
  #     )
  # Hibernate:
  #
  #     create table Actor_Movie (
  #        cast_id bigint not null,
  #         movies_id bigint not null,
  #         primary key (cast_id, movies_id)
  #     )
  # Hibernate:
  #
  #     create table Director (
  #        id bigint generated by default as identity,
  #         birthDate date,
  #         name varchar(255) not null,
  #         primary key (id)
  #     )
  # Hibernate:
  #
  #     create table Director_Movie (
  #        directors_id bigint not null,
  #         movies_id bigint not null,
  #         primary key (directors_id, movies_id)
  #     )
  # Hibernate:
  #
  #     create table Movie (
  #        id bigint generated by default as identity,
  #         caption varchar(255),
  #         externalRef varchar(255),
  #         name varchar(255) not null,
  #         releaseYear integer,
  #         runtimeMin integer not null,
  #         primary key (id)
  #     )
  # Hibernate:
  #
  #     create table Movie_genres (
  #        Movie_id bigint not null,
  #         genres varchar(255)
  #     )
