from flask import Flask
from flask_cors import CORS
from json import dumps

app = Flask(__name__)
cors = CORS(app, resources={r"/*": {"origins": "http://localhost:8100"}})



@app.route("/movie/<id>")
def movie(id):
  return dumps(
    {
      "id": id,
      "title": "asdasd",
      'year': 123,
      "externalRef": "a1231",
      "genres": ["Drama"]
    }
  )


@app.route("/movies")
def movies():
  return dumps(
    [{
      "id": 88286,
      "name": "Top Secret!",
      "caption": "Parody of WWII spy movies in which an American rock and roll singer becomes involved in a Resistance plot to rescue a scientist imprisoned in East Germany.",
      "releaseYear": 1984,
      "runtimeMin": 90,
      "cast": [
        {
          "id": 1088,
          "fullName": "Peter Cushing",
          "birthYear": 1913,
          "deathYear": 1994,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BNTM4NzE4NTIwNl5BMl5BanBnXkFtZTYwMTYxNzM2._V1_UX400.jpg"
        },
        {
          "id": 349706,
          "fullName": "Lucy Gutteridge",
          "birthYear": 1955,
          "deathYear": None,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BMTE1YzE4YjUtOWQ0OS00MTlhLWEzY2UtNDhjYmM4NjE4MDA5XkEyXkFqcGdeQXVyMjUyNDk2ODc@._V1_UX400.jpg"
        },
        {
          "id": 174,
          "fullName": "Val Kilmer",
          "birthYear": 1959,
          "deathYear": None,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BMTk3ODIzMDA5Ml5BMl5BanBnXkFtZTcwNDY0NTU4Ng@@._V1_UX400.jpg"
        },
        {
          "id": 447305,
          "fullName": "Jeremy Kemp",
          "birthYear": 1935,
          "deathYear": 2019,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BMTA4NDA3ODQzNzNeQTJeQWpwZ15BbWU3MDI1NjUxMTg@._V1_UX400.jpg"
        }
      ],
      "directors": [
        {
          "id": 720,
          "name": "Jim Abrahams",
          "birthYear": 1944,
          "deathYear": None,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BMTg2MDg4NjM3Nl5BMl5BanBnXkFtZTcwNTE3MjIzMQ@@._V1_UX400.jpg"
        },
        {
          "id": 1878,
          "name": "David Zucker",
          "birthYear": 1947,
          "deathYear": None,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BOTE1MmRkMTAtMzJhMC00ZTViLTg1NWYtNGM3Yjc0YWZkMDBhXkEyXkFqcGdeQXVyMTExMjU1MDQ3._V1_UX400.jpg"
        },
        {
          "id": 958387,
          "name": "Jerry Zucker",
          "birthYear": 1950,
          "deathYear": None,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BNTMyNDAyNDkyN15BMl5BanBnXkFtZTcwODA0NjgwMw@@._V1_UX400.jpg"
        }
      ],
      "externalRef": "tt0088286",
      "posterUrl": "https://m.media-amazon.com/images/M/MV5BMmNhMTZlMmItMDRkYy00YjBlLThiZTEtZDQ1ZjNmYWM4NWVkXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_UX400.jpg",
      "genres": [
        "COMEDY",
        "MUSIC"
      ]
    },
    {
      "id": 88323,
      "name": "The NeverEnding Story",
      "caption": "A troubled boy dives into a wondrous fantasy world through the pages of a mysterious book.",
      "releaseYear": 1984,
      "runtimeMin": 102,
      "cast": [
        {
          "id": 646768,
          "fullName": "Barret Oliver",
          "birthYear": 1973,
          "deathYear": None,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BMTk0ODY4MjI1Ml5BMl5BanBnXkFtZTcwOTE3MDkwMw@@._V1_UX400.jpg"
        },
        {
          "id": 574468,
          "fullName": "Gerald McRaney",
          "birthYear": 1947,
          "deathYear": None,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BMTIxMDY4NjQzNF5BMl5BanBnXkFtZTYwNTQ1ODgz._V1_UX400.jpg"
        },
        {
          "id": 441,
          "fullName": "Noah Hathaway",
          "birthYear": 1971,
          "deathYear": None,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BMjI3ODk5Njk1OV5BMl5BanBnXkFtZTcwMzIzNjMwNA@@._V1_UX400.jpg"
        },
        {
          "id": 660,
          "fullName": "Tami Stronach",
          "birthYear": 1972,
          "deathYear": None,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BNmE3NmIzMWYtYjdmNi00MjhhLWExZTUtNzY5MWRiODkyYmJmXkEyXkFqcGdeQXVyODM4ODMwMjA@._V1_UX400.jpg"
        }
      ],
      "directors": [
        {
          "id": 583,
          "name": "Wolfgang Petersen",
          "birthYear": 1941,
          "deathYear": None,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BMTczNTQ5ODY4OV5BMl5BanBnXkFtZTYwMDcyMDI1._V1_UX400.jpg"
        }
      ],
      "externalRef": "tt0088323",
      "posterUrl": "https://m.media-amazon.com/images/M/MV5BM2YwNWExYjItODZmOC00MTRjLWFlYmEtODFmNGI5M2E5NzYxXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX400.jpg",
      "genres": [
        "FAMILY",
        "ADVENTURE",
        "DRAMA"
      ]
    },
    {
      "id": 88680,
      "name": "After Hours",
      "caption": "An ordinary word processor has the worst night of his life after he agrees to visit a girl in Soho who he met that evening at a coffee shop.",
      "releaseYear": 1985,
      "runtimeMin": 97,
      "cast": [
        {
          "id": 1162,
          "fullName": "Griffin Dunne",
          "birthYear": 1955,
          "deathYear": None,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BOGZlNGJiNWUtNWQ3NS00MjUxLWFjZDYtYWE4MjA1OTRjY2VhXkEyXkFqcGdeQXVyNzgxNzcwOQ@@._V1_UX400.jpg"
        },
        {
          "id": 1045,
          "fullName": "Tommy Chong",
          "birthYear": 1938,
          "deathYear": None,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BMjE1MzYxNDg4Nl5BMl5BanBnXkFtZTYwMzU4NzYz._V1_UX400.jpg"
        },
        {
          "id": 89244,
          "fullName": "Verna Bloom",
          "birthYear": 1938,
          "deathYear": 2019,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BMTgxNDk2NTY0M15BMl5BanBnXkFtZTcwNTcwMzYwOA@@._V1_UX400.jpg"
        },
        {
          "id": 275,
          "fullName": "Rosanna Arquette",
          "birthYear": 1959,
          "deathYear": None,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BNTI0MjYxMzQ0OF5BMl5BanBnXkFtZTcwMTk0OTE3NQ@@._V1_UX400.jpg"
        }
      ],
      "directors": [
        {
          "id": 217,
          "name": "Martin Scorsese",
          "birthYear": 1942,
          "deathYear": None,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BMTcyNDA4Nzk3N15BMl5BanBnXkFtZTcwNDYzMjMxMw@@._V1_UX400.jpg"
        }
      ],
      "externalRef": "tt0088680",
      "posterUrl": "https://m.media-amazon.com/images/M/MV5BMTUxMjEzMzI2MV5BMl5BanBnXkFtZTgwNTU3ODAxMDE@._V1_UX400.jpg",
      "genres": [
        "CRIME",
        "COMEDY",
        "DRAMA"
      ]
    },
    {
      "id": 88763,
      "name": "Back to the Future",
      "caption": "Marty McFly, a 17-year-old high school student, is accidentally sent thirty years into the past in a time-traveling DeLorean invented by his close friend, the eccentric scientist Doc Brown.",
      "releaseYear": 1985,
      "runtimeMin": 116,
      "cast": [
        {
          "id": 670,
          "fullName": "Lea Thompson",
          "birthYear": 1961,
          "deathYear": None,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BZjgzNmViYTktMjhjNy00NDc1LWI2MjMtNGUxYmFlZTNhODViXkEyXkFqcGdeQXVyMjA3MjIzMDA@._V1_UX400.jpg"
        },
        {
          "id": 417,
          "fullName": "Crispin Glover",
          "birthYear": 1964,
          "deathYear": None,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BNjNjMzg2YWEtOTY4Ny00MDJiLTg1NWYtOGI5NmU2ZTE5OTI4XkEyXkFqcGdeQXVyMjQwMDg0Ng@@._V1_UX400.jpg"
        },
        {
          "id": 502,
          "fullName": "Christopher Lloyd",
          "birthYear": 1938,
          "deathYear": None,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BMTkxNzQ0ODgxOV5BMl5BanBnXkFtZTcwMTAxMDY0Mg@@._V1_UX400.jpg"
        },
        {
          "id": 150,
          "fullName": "Michael J. Fox",
          "birthYear": 1961,
          "deathYear": None,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BMTcwNzM0MjE4NF5BMl5BanBnXkFtZTcwMDkxMzEwMw@@._V1_UX400.jpg"
        }
      ],
      "directors": [
        {
          "id": 709,
          "name": "Robert Zemeckis",
          "birthYear": 1951,
          "deathYear": None,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BMTgyMTMzMDUyNl5BMl5BanBnXkFtZTcwODA0ODMyMw@@._V1_UX400.jpg"
        }
      ],
      "externalRef": "tt0088763",
      "posterUrl": "https://m.media-amazon.com/images/M/MV5BZmU0M2Y1OGUtZjIxNi00ZjBkLTg1MjgtOWIyNThiZWIwYjRiXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_UX400.jpg",
      "genres": [
        "SCI_FI",
        "COMEDY",
        "ADVENTURE"
      ]
    },
    {
      "id": 88846,
      "name": "Brazil",
      "caption": "A bureaucrat in a dystopic society becomes an enemy of the state as he pursues the woman of his dreams.",
      "releaseYear": 1985,
      "runtimeMin": 132,
      "cast": [
        {
          "id": 134,
          "fullName": "Robert De Niro",
          "birthYear": 1943,
          "deathYear": None,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BMjAwNDU3MzcyOV5BMl5BanBnXkFtZTcwMjc0MTIxMw@@._V1_UX400.jpg"
        },
        {
          "id": 596,
          "fullName": "Jonathan Pryce",
          "birthYear": 1947,
          "deathYear": None,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BNTI2YTEzYzItN2FlZi00M2Y3LTlhZjUtNmIyODU0ODRkN2E4XkEyXkFqcGdeQXVyMjQwMDg0Ng@@._V1_UX400.jpg"
        },
        {
          "id": 1340,
          "fullName": "Katherine Helmond",
          "birthYear": 1929,
          "deathYear": 2019,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BMTU2MjIwOTE0Nl5BMl5BanBnXkFtZTcwNjcwNDAzMQ@@._V1_UX400.jpg"
        },
        {
          "id": 2114,
          "fullName": "Kim Greist",
          "birthYear": 1958,
          "deathYear": None,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BMTkxMjY3NzQ5M15BMl5BanBnXkFtZTYwODMyODk2._V1_UX400.jpg"
        }
      ],
      "directors": [
        {
          "id": 416,
          "name": "Terry Gilliam",
          "birthYear": 1940,
          "deathYear": None,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BODAyMDM0NjEwOF5BMl5BanBnXkFtZTcwMTUyMDY5Mg@@._V1_UX400.jpg"
        }
      ],
      "externalRef": "tt0088846",
      "posterUrl": "https://m.media-amazon.com/images/M/MV5BMzIwM2IwYTItYmM4Zi00OWMzLTkwNjAtYWRmYWNmY2RhMDk0XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_UX400.jpg",
      "genres": [
        "SCI_FI",
        "DRAMA"
      ]
    },
    {
      "id": 88847,
      "name": "The Breakfast Club",
      "caption": "Five high school students meet in Saturday detention and discover how they have a lot more in common than they thought.",
      "releaseYear": 1985,
      "runtimeMin": 97,
      "cast": [
        {
          "id": 639,
          "fullName": "Ally Sheedy",
          "birthYear": 1962,
          "deathYear": None,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BMTc2ODUwNzM5NV5BMl5BanBnXkFtZTYwMTcxNzM3._V1_UX400.jpg"
        },
        {
          "id": 555,
          "fullName": "Judd Nelson",
          "birthYear": 1959,
          "deathYear": None,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BMTg3ODUwODQ5OV5BMl5BanBnXkFtZTcwMTUzNzQyMg@@._V1_UX400.jpg"
        },
        {
          "id": 389,
          "fullName": "Emilio Estevez",
          "birthYear": 1962,
          "deathYear": None,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BMjMzZGJlNDctYTE4Yy00MjI5LThlYzAtNjJhNDdlNmM2ZDM5XkEyXkFqcGdeQXVyMTQ4NjgzNA@@._V1_UX400.jpg"
        },
        {
          "id": 208,
          "fullName": "Molly Ringwald",
          "birthYear": 1968,
          "deathYear": None,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BMjM4MzM1NTkxNF5BMl5BanBnXkFtZTgwMzA4NTk3MjE@._V1_UX400.jpg"
        }
      ],
      "directors": [
        {
          "id": 455,
          "name": "John Hughes",
          "birthYear": 1950,
          "deathYear": 2009,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BMTExMzMxNDE4MzdeQTJeQWpwZ15BbWU3MDI5NjI2NzI@._V1_UX400.jpg"
        }
      ],
      "externalRef": "tt0088847",
      "posterUrl": "https://m.media-amazon.com/images/M/MV5BOTM5N2ZmZTMtNjlmOS00YzlkLTk3YjEtNTU1ZmQ5OTdhODZhXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_UX400.jpg",
      "genres": [
        "COMEDY",
        "DRAMA"
      ]
    },
    {
      "id": 88930,
      "name": "Clue",
      "caption": "Six guests are anonymously invited to a strange mansion for dinner, but after their host is killed, they must cooperate with the staff to identify the murderer as the bodies pile up.",
      "releaseYear": 1985,
      "runtimeMin": 94,
      "cast": [
        {
          "id": 1404,
          "fullName": "Madeline Kahn",
          "birthYear": 1942,
          "deathYear": 1999,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BNTkzMDkzMTI0OF5BMl5BanBnXkFtZTcwNTUzOTAxNQ@@._V1_UX400.jpg"
        },
        {
          "id": 502,
          "fullName": "Christopher Lloyd",
          "birthYear": 1938,
          "deathYear": None,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BMTkxNzQ0ODgxOV5BMl5BanBnXkFtZTcwMTAxMDY0Mg@@._V1_UX400.jpg"
        },
        {
          "id": 347,
          "fullName": "Tim Curry",
          "birthYear": 1946,
          "deathYear": None,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BMTc5MTQ2OTYwOF5BMl5BanBnXkFtZTYwMzEzMTA1._V1_UX400.jpg"
        },
        {
          "id": 107281,
          "fullName": "Eileen Brennan",
          "birthYear": 1932,
          "deathYear": 2013,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BMTYxODQ5MDAxM15BMl5BanBnXkFtZTcwNzkzMDYwMw@@._V1_UX400.jpg"
        }
      ],
      "directors": [
        {
          "id": 528718,
          "name": "Jonathan Lynn",
          "birthYear": 1943,
          "deathYear": None,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BNTgwNzUyNzMxN15BMl5BanBnXkFtZTgwODQwNzY1MzE@._V1_UX400.jpg"
        }
      ],
      "externalRef": "tt0088930",
      "posterUrl": "https://m.media-amazon.com/images/M/MV5BM2VlNTE1ZmMtOTAyNS00ODYwLWFmY2MtZWEzOTE2YjE1NDE2XkEyXkFqcGdeQXVyNDk3NzU2MTQ@._V1_UX400.jpg",
      "genres": [
        "MYSTERY",
        "CRIME",
        "COMEDY"
      ]
    },
    {
      "id": 88933,
      "name": "Cocoon",
      "caption": "When a group of trespassing seniors swim in a pool containing alien cocoons, they find themselves energized with youthful vigor.",
      "releaseYear": 1985,
      "runtimeMin": 117,
      "cast": [
        {
          "id": 2025,
          "fullName": "Hume Cronyn",
          "birthYear": 1911,
          "deathYear": 2003,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BNjY3OTUxMTgwNF5BMl5BanBnXkFtZTYwNDQwMTM2._V1_UX400.jpg"
        },
        {
          "id": 747,
          "fullName": "Don Ameche",
          "birthYear": 1908,
          "deathYear": 1993,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BMTMyNjc5ODE4MV5BMl5BanBnXkFtZTYwOTYwMTM2._V1_UX400.jpg"
        },
        {
          "id": 979,
          "fullName": "Wilford Brimley",
          "birthYear": 1934,
          "deathYear": 2020,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BMDIyMDg0M2ItZTU5Yi00MzM2LWFjYzItMWZlZjc1MDk3NmZlXkEyXkFqcGdeQXVyMTI3MDk3MzQ@._V1_UX400.jpg"
        },
        {
          "id": 1133,
          "fullName": "Brian Dennehy",
          "birthYear": 1938,
          "deathYear": 2020,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BNDE4MjEzMzAzMF5BMl5BanBnXkFtZTcwMzYzNDgwMw@@._V1_UX400.jpg"
        }
      ],
      "directors": [
        {
          "id": 165,
          "name": "Ron Howard",
          "birthYear": 1954,
          "deathYear": None,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BMTkzMDczMjUxNF5BMl5BanBnXkFtZTcwODY1Njk5Mg@@._V1_UX400.jpg"
        }
      ],
      "externalRef": "tt0088933",
      "posterUrl": "https://m.media-amazon.com/images/M/MV5BYzFiMWRiZTItMjViNy00ZjUzLTg1MTMtNTU4OGRlNzA2MTc2L2ltYWdlXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_UX400.jpg",
      "genres": [
        "SCI_FI",
        "COMEDY",
        "DRAMA"
      ]
    },
    {
      "id": 88939,
      "name": "The Color Purple",
      "caption": "A black Southern woman struggles to find her identity after suffering abuse from her father and others over four decades.",
      "releaseYear": 1985,
      "runtimeMin": 154,
      "cast": [
        {
          "id": 155,
          "fullName": "Whoopi Goldberg",
          "birthYear": 1955,
          "deathYear": None,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BMjE4ODgzNjQwOV5BMl5BanBnXkFtZTYwNzczNzc0._V1_UX400.jpg"
        },
        {
          "id": 418,
          "fullName": "Danny Glover",
          "birthYear": 1946,
          "deathYear": None,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BMTI4ODM2MzQwN15BMl5BanBnXkFtZTcwMjY2OTI5MQ@@._V1_UX400.jpg"
        },
        {
          "id": 1923,
          "fullName": "Margaret Avery",
          "birthYear": 1944,
          "deathYear": None,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BMTQyNzcwOTUwMl5BMl5BanBnXkFtZTYwNzM5Nzc4._V1_UX400.jpg"
        },
        {
          "id": 1856,
          "fullName": "Oprah Winfrey",
          "birthYear": 1954,
          "deathYear": None,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BMTgzODE3MzYxNF5BMl5BanBnXkFtZTYwMzEyNjc4._V1_UX400.jpg"
        }
      ],
      "directors": [
        {
          "id": 229,
          "name": "Steven Spielberg",
          "birthYear": 1946,
          "deathYear": None,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BMTY1NjAzNzE1MV5BMl5BanBnXkFtZTYwNTk0ODc0._V1_UX400.jpg"
        }
      ],
      "externalRef": "tt0088939",
      "posterUrl": "https://m.media-amazon.com/images/M/MV5BZDRkOWQ5NGUtYTVmOS00ZjNhLWEwODgtOGI2MmUxNTBkMjU0XkEyXkFqcGdeQXVyMjUzOTY1NTc@._V1_UX400.jpg",
      "genres": [
        "DRAMA"
      ]
    },
    {
      "id": 88944,
      "name": "Commando",
      "caption": "A retired Special Forces colonel tries to save his daughter, who was abducted by his former subordinate.",
      "releaseYear": 1985,
      "runtimeMin": 90,
      "cast": [
        {
          "id": 216,
          "fullName": "Arnold Schwarzenegger",
          "birthYear": 1947,
          "deathYear": None,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BMTI3MDc4NzUyMV5BMl5BanBnXkFtZTcwMTQyMTc5MQ@@._V1_UX400.jpg"
        },
        {
          "id": 1044,
          "fullName": "Rae Dawn Chong",
          "birthYear": 1961,
          "deathYear": None,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BMTM0NzU5MjIxOV5BMl5BanBnXkFtZTYwMTM1NzA3._V1_UX400.jpg"
        },
        {
          "id": 920460,
          "fullName": "Vernon Wells",
          "birthYear": 1945,
          "deathYear": None,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BMjAxMDY5MTc0NV5BMl5BanBnXkFtZTcwODg1NzcxOA@@._V1_UX400.jpg"
        },
        {
          "id": 445,
          "fullName": "Dan Hedaya",
          "birthYear": 1940,
          "deathYear": None,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BMjE0MzQ3MjY3Nl5BMl5BanBnXkFtZTYwNTA4MzE0._V1_UX400.jpg"
        }
      ],
      "directors": [
        {
          "id": 504495,
          "name": "Mark L. Lester",
          "birthYear": 1946,
          "deathYear": None,
          "posterUrl": "https://m.media-amazon.com/images/M/MV5BMjEzMzAyNzU1Ml5BMl5BanBnXkFtZTgwMzAxMzIzNTE@._V1_UX400.jpg"
        }
      ],
      "externalRef": "tt0088944",
      "posterUrl": "https://m.media-amazon.com/images/M/MV5BZWE0ZjFhYjItMzI5MC00MDllLWE4OGMtMzlhNGQzN2RjN2MwXkEyXkFqcGdeQXVyNDc2NjEyMw@@._V1_UX400.jpg",
      "genres": [
        "ACTION",
        "THRILLER",
        "ADVENTURE"
      ]
    }
    ]
  )


if __name__ == '__main__':
  app.run()