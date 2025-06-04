package com.animebase.animood_backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

// Lombok annotations (jeśli dodałeś Lombok jako zależność)
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data // Automatycznie generuje gettery, settery, toString(), equals(), hashCode()
@NoArgsConstructor // Automatycznie generuje konstruktor bezargumentowy
@AllArgsConstructor // Automatycznie generuje konstruktor z wszystkimi argumentami
@Document(collection = "anime_series") // Oznacza tę klasę jako dokument MongoDB i określa nazwę kolekcji
public class Anime {

    @Id // Oznacza to pole jako identyfikator dokumentu w MongoDB
    private String id; // Unikalny identyfikator dla każdego anime (MongoDB może go generować automatycznie)

    private String title; // Tytuł anime
    private List<String> genres; // Lista gatunków dla anime
    private String description; // Opis anime
    private String imageUrl; // <-- DODANE NOWE POLE

    // Jeśli nie używasz Lombok, musisz ręcznie dodać:
    // - Konstruktor bezargumentowy
    // - Konstruktor z wszystkimi polami (lub wybranymi)
    // - Gettery i settery dla wszystkich pól
    // - Opcjonalnie metody toString(), equals(), hashCode()
}
