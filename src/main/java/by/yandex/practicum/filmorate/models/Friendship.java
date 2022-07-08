package by.yandex.practicum.filmorate.models;

import java.util.Objects;

public class Friendship {
    private Long id;
    private final User user;
    private final User friend;
    private FriendshipStatus status;

    public Friendship(User user, User friend) {
        this.user = user;
        this.friend = friend;
        this.status = FriendshipStatus.NOT_CONFIRMED;
    }

    public Friendship(User user, User friend,  FriendshipStatus status) {
        this.user = user;
        this.friend = friend;
        this.status = status;
    }

    public Friendship(Long id, User user, User friend) {
        this.id = id;
        this.user = user;
        this.friend = friend;
        this.status = FriendshipStatus.NOT_CONFIRMED;
    }

    public Friendship(Long id, User user, User friend, FriendshipStatus status) {
        this.id = id;
        this.user = user;
        this.friend = friend;
        this.status = status;
    }

    public Long getId() {
        return this.id;
    }

    public FriendshipStatus getStatus() {
        return this.status;
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

    public void setStatus(FriendshipStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Friendship)) return false;
        Friendship that = (Friendship) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
