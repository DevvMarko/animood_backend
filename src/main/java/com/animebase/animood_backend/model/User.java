package com.animebase.animood_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users") // Będziemy przechowywać użytkowników w kolekcji "users"
public class User {

    @Id
    private String id;

    private String username;

    // Lista przechowująca ID ulubionych anime danego użytkownika
    private List<String> favoriteAnimeIds = new ArrayList<>();
}