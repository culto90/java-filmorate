package by.yandex.practicum.filmorate.storages;

import java.util.List;

public interface Storage<E, T> {
    List<E> getAll();
    E getById(T id);
    E put(E entity);
    E remove(E entity);
    void removeAll();
}
