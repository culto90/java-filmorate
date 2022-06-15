package by.yandex.practicum.filmorate.models;

import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ToString
public class User {
    private Long id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;
    private List<Friendship> friendships;

    public User() {
        friendships = new ArrayList<>();
    }

    public User (Long id, String email, String login, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
        friendships = new ArrayList<>();
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

    public void addFriendship(Friendship friendship) {
        this.friendships.add(friendship);
    }

    public Friendship getFriendshipById(Long friendshipId) {
        return friendships.stream()
                .filter(f -> f.getId() == friendshipId)
                .findFirst()
                .orElse(null);
    }

    public void removeFriendship(Friendship friendship) {
        this.friendships.remove(friendship);
    }


}
