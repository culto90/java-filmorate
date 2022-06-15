package by.yandex.practicum.filmorate.models;

import lombok.ToString;

@ToString
public class Friendship {
    long id;
    User user;
    User friend;

    public Friendship(User user, User friend) {
        this.user = user;
        this.friend = friend;
    }

    public Friendship(Long id, User user, User friend) {
        this.id = id;
        this.user = user;
        this.friend = friend;
    }

    public long getId() {
        return this.id;
    }
    public User getUser() {
        return this.user;
    }

    public User getFriend() {
        return this.friend;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
