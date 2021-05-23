# PA165_2021
PA165 project at MUNI

## Local deployment
mvn clean install & cd rest & mvn spring-boot:run

## Description  

**_Movies Recommender Catalogue_** is a web application which provides a **catalogue** of movies and **recommendations** 
for the user. Catalogue contains movies of different genres. User can like or dislike the movies they have watched.
By picking a movie, user gets the list of similar movies and movies that were liked
the most by other users who watched the same movie. 

## Tests
Run unit test with:
```
$ mvn clean test
```

## Diagrams

### Use case diagram
![Use case diagram](doc/use_case_diagram.png)

### Class diagram
![Class diagram](doc/class_diagram.png)
