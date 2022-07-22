package by.yandex.practicum.filmorate.storages;

import by.yandex.practicum.filmorate.models.Director;

public interface DirectorStorage extends Storage<Director,Long> {

    Director add(Director director);
}
