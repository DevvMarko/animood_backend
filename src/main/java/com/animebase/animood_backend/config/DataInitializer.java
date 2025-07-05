package com.animebase.animood_backend.config;

import com.animebase.animood_backend.model.User;
import com.animebase.animood_backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    public DataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Sprawdź, czy kolekcja użytkowników jest pusta
        if (userRepository.count() == 0) {
            System.out.println("Brak użytkowników w bazie. Inicjalizuję przykładowych użytkowników...");

            // Tworzymy przykładowych użytkowników
            User user1 = new User(null, "Jan Kowalski", List.of());
            User user2 = new User(null, "Anna Nowak", List.of());
            User user3 = new User(null, "Admin", List.of());

            // Zapisujemy ich do bazy danych
            userRepository.saveAll(List.of(user1, user2, user3));

            System.out.println("Dodano " + userRepository.count() + " użytkowników.");
        } else {
            System.out.println("Znaleziono użytkowników w bazie. Inicjalizacja nie jest potrzebna.");
        }
    }
}
