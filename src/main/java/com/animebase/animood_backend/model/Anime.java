package com.animebase.animood_backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

// Lombok annotations (jeśli dodałeś Lombok jako zależność)
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "anime_series")
public class Anime {

    @Id
    private String id;

    private String title;
    private List<String> genres;
    private String description;
    private String imageUrl;

}
