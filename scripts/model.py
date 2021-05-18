from dataclasses import dataclass
from typing import List, Optional


@dataclass
class Movie:
  id: int
  title: str
  year: int
  caption: str
  genres: List[str]
  externalRef: str
  posterUrl: str
  runtimeMin: int


@dataclass
class Actor:
  id: int
  externalRef: str
  birthYear: int
  deathYear: Optional[int]
  name: str
  posterUrl: str


@dataclass
class Director:
  id: int
  externalRef: str
  name: str
  birthYear: int
  deathYear: Optional[int]
  posterUrl: str


@dataclass
class Rating:
  userId: int
  movieId: int
  rating: str


@dataclass
class User:
  id: int
  name: str
  email: str
  passHash: str
  isAdmin: bool
