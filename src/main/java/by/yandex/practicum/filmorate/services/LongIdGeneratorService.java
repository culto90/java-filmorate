package by.yandex.practicum.filmorate.services;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class LongIdGeneratorService implements IdGeneratorService<Long> {
    private static final AtomicLong atomicLong = new AtomicLong(1);
    @Override
    public Long getId() {
        return atomicLong.getAndIncrement();
    }
}
