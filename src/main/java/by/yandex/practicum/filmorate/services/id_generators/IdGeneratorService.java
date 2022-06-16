package by.yandex.practicum.filmorate.services.id_generators;

public interface IdGeneratorService<T> {
    T getUserId();
    T getFilmId();
    T getFriendshipId();
    T getLikeId();
}
