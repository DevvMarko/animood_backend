package com.animebase.animood_backend.service;

import com.animebase.animood_backend.model.Anime;
import com.animebase.animood_backend.model.User;
import com.animebase.animood_backend.repository.AnimeRepository;
import com.animebase.animood_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AnimeRepository animeRepository;

    public UserService(UserRepository userRepository, AnimeRepository animeRepository) {
        this.userRepository = userRepository;
        this.animeRepository = animeRepository;
    }

    // Metoda do pobierania wszystkich użytkowników
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Metoda do dodawania anime do ulubionych
    public User addAnimeToFavorites(String userId, String animeId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // Sprawdzamy, czy anime o danym ID istnieje
        animeRepository.findById(animeId)
                .orElseThrow(() -> new RuntimeException("Anime not found with id: " + animeId));

        // Dodajemy ID anime do listy ulubionych, jeśli jeszcze go tam nie ma
        if (!user.getFavoriteAnimeIds().contains(animeId)) {
            user.getFavoriteAnimeIds().add(animeId);
            userRepository.save(user);
        }
        return user;
    }

    // Metoda do pobierania ulubionych anime danego użytkownika
    public List<Anime> getFavoriteAnime(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // Pobieramy wszystkie anime, których ID znajdują się na liście ulubionych
        return animeRepository.findAllById(user.getFavoriteAnimeIds());
    }

    // Nowa metoda do usuwania anime z ulubionych
    public User removeAnimeFromFavorites(String userId, String animeId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // Usuwamy ID anime z listy ulubionych
        user.getFavoriteAnimeIds().remove(animeId);

        // Zapisujemy zaktualizowanego użytkownika
        return userRepository.save(user);
    }
}