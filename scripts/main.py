import csv
from concurrent.futures.thread import ThreadPoolExecutor

import requests
from bs4 import BeautifulSoup as Soup

from model import *
from write_sql import *


def nullable(v):
  return None if v == '\\N' else v


def getImdbPhotosUrls(ids):
  buffer = dict()
  with ThreadPoolExecutor(80) as pool:
    while ids:
      pool.submit(fetchImdbPosterUrl, ids.pop(), buffer)

  return buffer


def fetchImdbPosterUrl(id, buffer):
  print(f"fetching principal {id}")
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
  with open("./imdb/csv/title_basics.tsv", newline='') as csvfile:
    csv_reader = csv.reader(csvfile, delimiter='\t')
    next(csv_reader)

    return {x[0]: x for x in csv_reader if x[1] == "movie"}


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
  posterUrl = urlNormalize(omdbMovie[0], 400)
  return Movie(int(csvMovie[0][2:]), csvMovie[2].replace("'", "''"), csvMovie[5],
               caption=omdbMovie[1].replace("'", "''"), posterUrl=posterUrl,
               genres=csvMovie[8].split(','), externalRef=csvMovie[0], runtimeMin=csvMovie[7])


def createActor(csvActor, poster) -> Actor:
  posterUrl = urlNormalize(poster, 400) if poster else "https://m.media-amazon.com/images/S/sash/9FayPGLPcrscMjU.png"
  return Actor(int(csvActor[0][2:]), csvActor[0], nullable(csvActor[2]), nullable(csvActor[3]),
               csvActor[1].replace("'", "''"),
               posterUrl)


def createDirector(csvDirector, poster) -> Director:
  posterUrl = urlNormalize(poster, 400) if poster else "https://m.media-amazon.com/images/S/sash/9FayPGLPcrscMjU.png"
  return Director(int(csvDirector[0][2:]), csvDirector[0], csvDirector[1].replace("'", "''"), nullable(csvDirector[2]),
                  nullable(csvDirector[3]),
                  posterUrl)


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


def urlNormalize(url: str, size: int) -> str:
  base = url.split("_V1")[0]
  return f"{base}_V1_UX{size}.jpg"


if __name__ == '__main__':
  moviesToGenerate = 10_000
  offset = 0

  movies = getMovies()
  print("movies loaded")

  ratings = getMovieRatings()
  print("ratings loaded")

  topMovies = list(filter(lambda x: int(ratings.get(x[0], [0, 0, 0])[2]) > 50000, movies.items()))
  topMovies.sort(key=lambda x: float(ratings.get(x[0], [0, 0])[1]), reverse=True)
  topMovies = topMovies[offset:(offset + moviesToGenerate)]
  topMoviesIds = {m[0] for m in topMovies}

  moviePrincipalMappings = getMoviePrincipalMappings(topMoviesIds)
  movieActorsMappings = {(x[0], x[1]) for x in moviePrincipalMappings if int(x[2]) in {7, 8}}
  print("mappins1 loaded")

  movieDirectorMappings = {(x[0], x[1]) for x in moviePrincipalMappings if int(x[2]) == 2}
  print("mappins2 loaded")

  topMoviesActorIds = {mapping[1] for mapping in movieActorsMappings}
  topMoviesDirectorIds = {mapping[1] for mapping in movieDirectorMappings}

  omdbMovies = getOmdbMovies(topMoviesIds.copy())
  decoratedMovies = [createMovie(movie[1], omdbMovies.get(movie[0])) for movie in topMovies if movie[0] in omdbMovies]

  principals = getPrincipals(topMoviesActorIds.union(topMoviesDirectorIds))
  print("principals basics loaded")

  actors = [a[1] for a in principals.items() if a[0] in topMoviesActorIds]
  directors = [d[1] for d in principals.items() if d[0] in topMoviesDirectorIds]

  print(f"going to fetch {len(topMoviesDirectorIds.union(topMoviesActorIds))} photos")
  principalsPosters = getImdbPhotosUrls(list(topMoviesDirectorIds.union(topMoviesActorIds)).copy())

  decoratedActors = [createActor(a, principalsPosters.get(a[0])) for a in actors]
  decoratedDirectors = [createDirector(d, principalsPosters.get(d[0])) for d in directors]

  for a in decoratedActors:
    print(a)

  for m in decoratedMovies:
    print(m)

  for a in decoratedDirectors:
    print(a)

  for m in movieDirectorMappings:
    print(m)

  for m in movieActorsMappings:
    print(m)

  print(f"total of {len(decoratedMovies)} "
        f"movies  with {len(decoratedActors)} actors, "
        f"{len(decoratedDirectors)} directors "
        f"{len(movieActorsMappings)} actor mappings "
        f"{len(movieDirectorMappings)} director mappings")

  append = True if offset > 0 else False

  print("writing movies")
  writeMovies(decoratedMovies, append)
  writeActors(decoratedActors, append)
  writeDirectors(decoratedDirectors, append)
  writeMovieDirectorMappings(list(movieDirectorMappings), append)
  writeMovieActorMappings(list(movieActorsMappings), append)
