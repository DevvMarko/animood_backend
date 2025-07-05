package com.animebase.animood_backend.controller;

import com.animebase.animood_backend.model.Anime;
import com.animebase.animood_backend.service.AnimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/anime")
public class AnimeController {

    private final AnimeService animeService;

    @Autowired
    public AnimeController(AnimeService animeService) {
        this.animeService = animeService;
    }

    @PostMapping
    public ResponseEntity<Anime> createAnime(@RequestBody Anime anime) {
        Anime createdAnime = animeService.createAnime(anime);
        return new ResponseEntity<>(createdAnime, HttpStatus.CREATED);
    }

    // Endpoint do pobierania wszystkich anime
    // HTTP GET na /api/anime
    @GetMapping
    public ResponseEntity<List<Anime>> getAllAnime(
            @RequestParam(required = false) String title, // Dodajemy parametr title
            @RequestParam(required = false) List<String> genres,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDir) {

        Sort sort;
        if (sortBy != null && !sortBy.isEmpty()) {
            Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
            sort = Sort.by(direction, sortBy);
        } else {
            sort = Sort.by(Sort.Direction.ASC, "id");
        }

        List<Anime> animeList = animeService.getAllAnime(title, genres, sort);
        return new ResponseEntity<>(animeList, HttpStatus.OK);
    }


    // Endpoint do pobierania pojedynczego anime po ID
    @GetMapping("/{id}")
    public ResponseEntity<Anime> getAnimeById(@PathVariable String id) {
        Optional<Anime> animeOptional = animeService.getAnimeById(id);
        return animeOptional.map(anime -> new ResponseEntity<>(anime, HttpStatus.OK)) // Jeśli znaleziono, zwraca 200 OK
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // Jeśli nie znaleziono, zwraca 404 Not Found
    }

    // Endpoint do aktualizacji istniejącego anime
    @PutMapping("/{id}")
    public ResponseEntity<Anime> updateAnime(@PathVariable String id, @RequestBody Anime animeDetails) {

        try {
            Anime updatedAnime = animeService.updateAnime(id, animeDetails);
            return new ResponseEntity<>(updatedAnime, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint do usuwania anime
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAnime(@PathVariable String id) {
        try {
            animeService.deleteAnime(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
