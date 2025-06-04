package com.animebase.animood_backend.service;

import com.animebase.animood_backend.model.Anime;
import com.animebase.animood_backend.repository.AnimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils; // Import dla StringUtils

import java.util.List;
import java.util.Optional;

@Service // Oznacza tę klasę jako komponent Springa (serwis)
public class AnimeService {

    private final AnimeRepository animeRepository; // Zależność do repozytorium

    @Autowired // Wstrzykiwanie zależności przez konstruktor (zalecane)
    public AnimeService(AnimeRepository animeRepository) {
        this.animeRepository = animeRepository;
    }

    // Zaktualizowana metoda do pobierania anime z wyszukiwaniem po tytule, filtrowaniem po gatunkach i sortowaniem
    public List<Anime> getAllAnime(String title, List<String> genres, Sort sort) {
        // Sprawdzamy, czy StringUtils.hasText jest lepsze niż title != null && !title.isEmpty()
        if (StringUtils.hasText(title)) {
            // Jeśli podano tytuł, wyszukuj po tytule (na razie ignorujemy genres, jeśli title jest obecny)
            // Później można to rozbudować o łączenie kryteriów
            return animeRepository.findByTitleContainingIgnoreCase(title, sort);
        } else if (genres != null && !genres.isEmpty()) {
            // Jeśli nie podano tytułu, ale podano gatunki, filtruj po gatunkach
            return animeRepository.findAnimeByAllGenres(genres, sort);
        } else {
            // Jeśli nie podano ani tytułu, ani gatunków, zwróć wszystkie (posortowane)
            return animeRepository.findAll(sort);
        }
    }


    // Metoda do pobierania pojedynczego anime po ID
    public Optional<Anime> getAnimeById(String id) {
        return animeRepository.findById(id);
    }

    // Metoda do tworzenia nowego anime
    public Anime createAnime(Anime anime) {
        // Tutaj w przyszłości można dodać logikę biznesową,
        // np. walidację, sprawdzenie czy anime już nie istnieje itp.
        return animeRepository.save(anime);
    }

    // Metoda do aktualizacji istniejącego anime
    // (Przykład prostej aktualizacji, można rozbudować o sprawdzanie czy ID istnieje)
    public Anime updateAnime(String id, Anime animeDetails) {
        // W bardziej zaawansowanej wersji należałoby najpierw pobrać anime,
        // zaktualizować jego pola i dopiero zapisać.
        // Dla uproszczenia zakładamy, że animeDetails ma ustawione poprawne ID.
        // Lub można zrobić:
        // Anime anime = animeRepository.findById(id).orElseThrow(() -> new RuntimeException("Anime not found"));
        // anime.setTitle(animeDetails.getTitle());
        // anime.setGenres(animeDetails.getGenres());
        // anime.setDescription(animeDetails.getDescription());
        // return animeRepository.save(anime);
        animeDetails.setId(id); // Upewniamy się, że ID jest ustawione
        return animeRepository.save(animeDetails);
    }

    // Metoda do usuwania anime
    public void deleteAnime(String id) {
        animeRepository.deleteById(id);
    }
}