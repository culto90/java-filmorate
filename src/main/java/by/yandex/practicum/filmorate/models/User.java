package by.yandex.practicum.filmorate.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class User {
    private Long id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;
    private Map<Long, Friendship> friendships;
    private Map<Long, Like> likes;

    public User() {
        this.friendships = new HashMap<>();
        this.likes = new HashMap<>();
    }

    public User(Long id, String email, String login, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
        this.friendships = new HashMap<>();
        this.likes = new HashMap<>();
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

    public List<Friendship> getFriendships() {
        return new ArrayList<>(this.friendships.values());
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
        this.friendships =  friendships
                .stream()
                .collect(Collectors.toMap(Friendship::getId, friendship -> friendship));
    }

    public void setLikeList(List<Like> likes) {
        this.likes = likes.stream()
                .collect(Collectors.toMap(Like::getId, like -> like));
    }

    public void addFriendship(Friendship friendship) {
        this.friendships.put(friendship.getId(), friendship);
    }

    public void addLike(Like like) {
        this.likes.put(like.getId(), like);
    }

    public Friendship getFriendshipById(Long friendshipId) {
        return friendships.values().stream()
                .filter(f -> f.getId().equals(friendshipId))
                .findFirst()
                .orElse(null);
    }

    public List<Like> getLikes() {
        return new ArrayList<>(likes.values());
    }

    public Like getLikeById(Long likeId) {
        return likes.values()
                .stream()
                .filter(l -> l.getId().equals(likeId))
                .findFirst()
                .orElse(null);
    }

    public void removeFriendship(Friendship friendship) {
        this.friendships.remove(friendship.getId(), friendship);
    }

    public void removeLike(Like like) {
        this.likes.remove(like.getId(), like);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && email.equals(user.email) && Objects.equals(login, user.login)
                && Objects.equals(name, user.name) && Objects.equals(birthday, user.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, login, name, birthday);
    }
}
