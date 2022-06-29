# java-filmorate
Template repository for Filmorate project.

Database ER Diagram:
![](https://github.com/culto90/java-filmorate/src/main/resources/etc/filmorate_er_diagram.PNG)

**USER**: stored data about user;
**FRIENDSHIP**: intersect table between two users, M:M relationship. Stored data about friendships;
**LIKE**: intersect table between users and films, M:M relationship. Stored data about likes;
**FILM**: stored data about films;
**MPA_RATING**: MPA Film Rating, dictionary, stored data about film ratings:

| CODE | DESCTIPTION                                                              |
|------|--------------------------------------------------------------------------|
| G    | The film has no age restrictions.                                        |
|   PG   | Children are encouraged to watch the movie with their parents.           |
|  PG-13    | Some material may be inappropriate for children under 13.                |
|    R  | Those under 17 must be accompanied by an adult (at least 18 years old).  |
|    NC-17  | Nobody under the age of 18 is admitted in any circumstance.              |          

**FILM_GENRE**: intersect table between films and genres, M:M relationship. Stored data about film genres;
**GENRE**: Dictionary, stored data about film genres;
