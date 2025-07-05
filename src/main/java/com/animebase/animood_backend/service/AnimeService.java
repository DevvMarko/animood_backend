package com.animebase.animood_backend.service;

import com.animebase.animood_backend.model.Anime;
import com.animebase.animood_backend.repository.AnimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class AnimeService {

    private final AnimeRepository animeRepository;

    @Autowired // Wstrzykiwanie zależności przez konstruktor (zalecane)
    public AnimeService(AnimeRepository animeRepository) {
        this.animeRepository = animeRepository;
    }

    // Zaktualizowana metoda do pobierania anime z wyszukiwaniem po tytule, filtrowaniem po gatunkach i sortowaniem
    public List<Anime> getAllAnime(String title, List<String> genres, Sort sort) {
        if (StringUtils.hasText(title)) {
            return animeRepository.findByTitleContainingIgnoreCase(title, sort);
        } else if (genres != null && !genres.isEmpty()) {
            return animeRepository.findAnimeByAllGenres(genres, sort);
        } else {
            return animeRepository.findAll(sort);
        }
    }

    // Metoda do pobierania pojedynczego anime po ID
    public Optional<Anime> getAnimeById(String id) {
        return animeRepository.findById(id);
    }

    // Metoda do tworzenia nowego anime
    public Anime createAnime(Anime anime) {
        return animeRepository.save(anime);
    }

    // Metoda do aktualizacji istniejącego anime
    public Anime updateAnime(String id, Anime animeDetails) {
        animeDetails.setId(id); // Upewniamy się, że ID jest ustawione
        return animeRepository.save(animeDetails);
    }

    // Metoda do usuwania anime
    public void deleteAnime(String id) {
        animeRepository.deleteById(id);
    }
}