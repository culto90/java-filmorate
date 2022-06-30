# java-filmorate
Template repository for Filmorate project.

Database ER Diagram:
![](https://github.com/culto90/java-filmorate/blob/main/filmorate_er_diagram.PNG)

**USERS**: stored data about user;

**FRIENDSHIPS**: intersect table between two users, M:M relationship. Stored data about friendships;

**LIKES**: intersect table between users and films, M:M relationship. Stored data about likes;

**FILMS**: stored data about films;

**MPA_RATINGS**: MPA Film Rating, dictionary, stored data about film ratings:


| CODE | DESCTIPTION                                                              |
|------|--------------------------------------------------------------------------|
| G    | The film has no age restrictions.                                        |
|   PG   | Children are encouraged to watch the movie with their parents.           |
|  PG-13    | Some material may be inappropriate for children under 13.                |
|    R  | Those under 17 must be accompanied by an adult (at least 18 years old).  |
|    NC-17  | Nobody under the age of 18 is admitted in any circumstance.              |          

**FILM_GENRES**: intersect table between films and genres, M:M relationship. Stored data about film genres;

**GENRES**: Dictionary, stored data about film genres;

**EXAMPLES:**
1. Get all users:

SELECT * FROM users;


2. Get All Friends current user:

SELECT * 

FROM users AS U 

  JOIN friendships AS f ON u.user_id = f.user_id
  
  JOIN users AS fu ON f.friend_id = fu.user_id;
  
  
3. Get all films:

SELECT * 

FROM films AS f

  LEFT JOIN mpa_ratings AS mpa ON f.mpa_rating_id = mpa.mpa_rating_id
  
  LEFT JOIN film_genres AS fg ON f.film_id = fg.film_id
  
  LEFT JOIN genres AS g ON fg.genre_id = g.genre_id;
  
  
4. Get all films and likes and users:

SELECT *

FROM films AS f

  LEFT JOIN likes AS l ON f.film_id = l.film_id
  
  LEFT JOIN users AS u ON l.user_id = u.user_id;
