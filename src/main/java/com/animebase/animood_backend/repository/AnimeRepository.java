package com.animebase.animood_backend.repository;

import com.animebase.animood_backend.model.Anime;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List; // Upewnij się, że masz ten import
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.Query; // Dodaj ten import


@Repository
public interface AnimeRepository extends MongoRepository<Anime, String> {

    @Query("{ 'genres': { $all: ?0 } }")
    List<Anime> findAnimeByAllGenres(List<String> genresList, Sort sort);

    @Query("{ 'genres': { $all: ?0 } }")
    List<Anime> findAnimeByAllGenres(List<String> genresList); // Wersja bez sortowania

    // Nowa metoda do wyszukiwania anime po części tytułu (case-insensitive) z sortowaniem
    List<Anime> findByTitleContainingIgnoreCase(String title, Sort sort);

    // Opcjonalnie, wersja bez sortowania lub z domyślnym sortowaniem
    List<Anime> findByTitleContainingIgnoreCase(String title);
}