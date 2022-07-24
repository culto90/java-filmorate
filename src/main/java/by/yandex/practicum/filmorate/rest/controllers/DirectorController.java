package by.yandex.practicum.filmorate.rest.controllers;

import by.yandex.practicum.filmorate.models.Director;
import by.yandex.practicum.filmorate.rest.converters.DirectorDtoToDirectorConverter;
import by.yandex.practicum.filmorate.rest.converters.DirectorToDirectorDtoConverter;
import by.yandex.practicum.filmorate.rest.dto.DirectorDto;
import by.yandex.practicum.filmorate.services.DirectorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/directors")
public class DirectorController {
    private final DirectorService directorService;
    private final DirectorToDirectorDtoConverter toDirectorDtoConverter;
    private final DirectorDtoToDirectorConverter toDirectorConverter;

    @Autowired
    public DirectorController(DirectorService directorService,
                              DirectorToDirectorDtoConverter directorDtoConverter,
                              DirectorDtoToDirectorConverter toDirectorConverter){
        this.directorService = directorService;
        this.toDirectorDtoConverter = directorDtoConverter;
        this.toDirectorConverter = toDirectorConverter;
    }

    @GetMapping
    public List<DirectorDto> getAll(){
        log.trace("Получен GET-запрос на список режиссёров.");
        return directorService.getAll()
                .stream()
                .map(toDirectorDtoConverter::convert)
                .collect(Collectors.toList());
    }

    @GetMapping("/{directorId}")
    public DirectorDto get(@PathVariable long directorId) {
        log.trace("Получен GET-запрос на режиссёра ID {}.", directorId);
        return toDirectorDtoConverter.convert(directorService.getById(directorId));
    }

    @PostMapping
    public DirectorDto add(@Valid @RequestBody DirectorDto directorDto) {
        Director director = toDirectorConverter.convert(directorDto);
        log.trace("Получен POST-запрос на добавление режиссёра {}.", director.getName());
        return toDirectorDtoConverter.convert(directorService.add(director));
    }

    @PutMapping
    public DirectorDto update(@Valid @RequestBody Director director) {
        log.trace("Получен PUT-запрос на обновление режиссёра {}.", director.getName());
        return toDirectorDtoConverter.convert(directorService.update(director));
    }

    @DeleteMapping("/{directorId}")
    public DirectorDto remove(@PathVariable long directorId) {
        log.trace("Получен DELETE-запрос на удаление режиссёра ID {}.", directorId);
        return toDirectorDtoConverter.convert(directorService.remove(directorId));
    }
}
