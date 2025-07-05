package com.animebase.animood_backend.controller;

import com.animebase.animood_backend.model.Anime;
import com.animebase.animood_backend.model.User;
import com.animebase.animood_backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api") // Ustawiamy bazowy mapping na /api
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"}) // Upewniamy się, że CORS jest włączony
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Endpoint: GET /api/users
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Endpoint: POST /api/users/{userId}/favorites/{animeId}
    @PostMapping("/users/{userId}/favorites/{animeId}")
    public ResponseEntity<User> addAnimeToFavorites(@PathVariable String userId, @PathVariable String animeId) {
        User updatedUser = userService.addAnimeToFavorites(userId, animeId);
        return ResponseEntity.ok(updatedUser);
    }

    // Endpoint: GET /api/users/{userId}/favorites
    @GetMapping("/users/{userId}/favorites")
    public ResponseEntity<List<Anime>> getFavoriteAnime(@PathVariable String userId) {
        return ResponseEntity.ok(userService.getFavoriteAnime(userId));
    }

    // Nowy endpoint: DELETE /api/users/{userId}/favorites/{animeId}
    @DeleteMapping("/users/{userId}/favorites/{animeId}")
    public ResponseEntity<User> removeAnimeFromFavorites(@PathVariable String userId, @PathVariable String animeId) {
        User updatedUser = userService.removeAnimeFromFavorites(userId, animeId);
        return ResponseEntity.ok(updatedUser);
    }
}