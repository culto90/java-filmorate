package by.yandex.practicum.filmorate.rest.controllers;

import by.yandex.practicum.filmorate.models.Director;
import by.yandex.practicum.filmorate.services.DirectorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/directors")
public class DirectorController {
    DirectorService directorService;

    @Autowired
    public DirectorController(DirectorService directorService){
        this.directorService = directorService;
    }

    @GetMapping
    public List<Director> getAll(){
        log.trace("Получен GET-запрос на список режиссёров.");
        return directorService.getAll();
    }

    @GetMapping("/{directorId}")
    public Director get(@PathVariable long directorId) {
        log.trace("Получен GET-запрос на режиссёра ID {}.", directorId);
        return directorService.getById(directorId);
    }

    @PostMapping
    public Director add(@Valid @RequestBody Director director) {
        log.trace("Получен POST-запрос на добавление режиссёра {}.", director.getName());
        return directorService.add(director);
    }

    @PutMapping
    public Director update(@Valid @RequestBody Director director) {
        log.trace("Получен PUT-запрос на обновление режиссёра {}.", director.getName());
        return directorService.update(director);
    }

    @DeleteMapping("/{directorId}")
    public Director remove(@PathVariable long directorId) {
        log.trace("Получен DELETE-запрос на удаление режиссёра ID {}.", directorId);
        return directorService.remove(directorId);
    }
}
