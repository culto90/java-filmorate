package by.yandex.practicum.filmorate.services.id_generators;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class LongIdGeneratorService implements IdGeneratorService<Long> {
    private static final AtomicLong userIdGenerator = new AtomicLong(1);
    private static final AtomicLong filmIdGenerator = new AtomicLong(1);
    private static final AtomicLong friendshipIdGenerator = new AtomicLong(1);
    private static final AtomicLong likeIdGenerator = new AtomicLong(1);

    @Override
    public Long getUserId() {
        return userIdGenerator.getAndIncrement();
    }

    @Override
    public Long getFilmId() {
        return filmIdGenerator.getAndIncrement();
    }

    @Override
    public Long getFriendshipId() {
        return friendshipIdGenerator.getAndIncrement();
    }

    @Override
    public Long getLikeId() {
        return likeIdGenerator.getAndIncrement();
    }
}
