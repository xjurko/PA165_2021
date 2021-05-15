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
        "id": 123,
        "title": "asdasd",
        'year': 123,
        "externalRef": "a1231",
        "genres": ["Drama"]
      },
      {
        "id": 312,
        "title": "asdasd",
        'year': 123,
        "externalRef": "a1231",
        "genres": ["Drama"]
      }
    ]
  )


if __name__ == '__main__':
  app.run()