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
    [
      {
        "id": 1,
        "name": "asdasd",
        'releaseYear': 1992,
        "externalRef": "a1231",
        "genres": ["Drama"]
      },
      {
        "id": 2,
        "name": "asdasd",
        'releaseYear': 2012,
        "externalRef": "a1231",
        "genres": ["Drama"]
      },
      {
              "id": 3,
              "name": "asdasd",
              'releaseYear': 1982,
              "externalRef": "a1231",
              "genres": ["Action", "Sci-Fi"]
      },
      {
              "id": 4,
              "name": "asdasd",
              'releaseYear': 2016,
              "externalRef": "a1231",
              "genres": ["Comedy"]
      },
      {
              "id": 5,
              "name": "asdasd",
              'releaseYear': 1972,
              "externalRef": "a1231",
              "genres": ["Fantasy"]
      },
      {
              "id": 6,
              "name": "asdasd",
              'releaseYear': 1988,
              "externalRef": "a1231",
              "genres": ["Drama"]
      },
      {
              "id": 9,
              "name": "asdasd",
              'releaseYear': 2020,
              "externalRef": "a1231",
              "genres": ["Action"]
      },
      {
              "id": 10,
              "name": "asdasd",
              'releaseYear': 2011,
              "externalRef": "a1231",
              "genres": ["Sci-fi"]
      },
      {
              "id": 11,
              "name": "asdasd",
              'releaseYear': 1996,
              "externalRef": "a1231",
              "genres": ["Documentary"]
      },
      {
              "id": 12,
              "name": "asdasd",
              'releaseYear': 2014,
              "externalRef": "a1231",
              "genres": ["Documentary"]
      },
      {
              "id": 13,
              "name": "asdasd",
              'releaseYear': 1961,
              "externalRef": "a1231",
              "genres": ["Drama"]
      },
      {
              "id": 14,
              "name": "asdasd",
              'releaseYear': 1987,
              "externalRef": "a1231",
              "genres": ["Melodrama"]
      },
      {
              "id": 15,
              "name": "asdasd",
              'releaseYear': 1991,
              "externalRef": "a1231",
              "genres": ["Cartoon"]
      }
    ]
  )


if __name__ == '__main__':
  app.run()