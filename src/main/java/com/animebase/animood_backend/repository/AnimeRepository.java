package com.animebase.animood_backend.repository;

import com.animebase.animood_backend.model.Anime;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.Query;


@Repository
public interface AnimeRepository extends MongoRepository<Anime, String> {

    @Query("{ 'genres': { $all: ?0 } }")
    List<Anime> findAnimeByAllGenres(List<String> genresList, Sort sort);

    @Query("{ 'genres': { $all: ?0 } }")
    List<Anime> findAnimeByAllGenres(List<String> genresList);

    List<Anime> findByTitleContainingIgnoreCase(String title, Sort sort);

    List<Anime> findByTitleContainingIgnoreCase(String title);
}