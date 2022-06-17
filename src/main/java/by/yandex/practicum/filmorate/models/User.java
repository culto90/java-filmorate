package by.yandex.practicum.filmorate.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class User {
    private Long id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;
    private List<Friendship> friendships;
    private List<Like> likes;

    public User() {
        this.friendships = new ArrayList<>();
        this.likes = new ArrayList<>();
    }

    public User (Long id, String email, String login, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
        this.friendships = new ArrayList<>();
        this.likes = new ArrayList<>();
    }

    public void setName(String name) {
        if (name.isEmpty()) {
            this.name = login;
        } else {
            this.name = name;
        }
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public List<Friendship>  getFriendships() {
        return this.friendships;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public void setFriendshipList(List<Friendship> friendships) {
        this.friendships = new ArrayList<>(friendships);
    }

    public void setLikeList(List<Like> likes) {
        this.likes = new ArrayList<>(likes);
    }

    public void addFriendship(Friendship friendship) {
        this.friendships.add(friendship);
    }

    public void addLike(Like like) {
        this.likes.add(like);
    }

    public Friendship getFriendshipById(Long friendshipId) {
        return friendships.stream()
                .filter(f -> f.getId().equals(friendshipId))
                .findFirst()
                .orElse(null);
    }

    public List<Like> getLikes() {
        return likes;
    }

    public Like getLikeById(Long likeId) {
        return likes.stream()
                .filter(l -> l.getId().equals(likeId))
                .findFirst()
                .orElse(null);
    }

    public void removeFriendship(Friendship friendship) {
        this.friendships.remove(friendship);
    }

    public void removeLike(Like like) {
        this.likes.remove(like);
    }

    @Override
    public String toString() {
        return "User(" +
                "id=" + this.getId() + ", " +
                "email=" + this.getEmail() + ", " +
                "login=" + this.getLogin() + ", " +
                "name=" + this.getName() + ", " +
                "birthday=" + this.getBirthday().format(DateTimeFormatter.ISO_LOCAL_DATE) +
                ")";
    }
}
