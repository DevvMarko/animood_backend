# Etap 1: Budowanie aplikacji przy użyciu Mavena
# Używamy oficjalnego obrazu Maven z JDK 17 (Temurin to następca AdoptOpenJDK)
FROM maven:3.9-eclipse-temurin-17 AS builder

# Ustawiamy katalog roboczy w kontenerze
WORKDIR /app

# Kopiujemy pliki potrzebne do pobrania zależności Maven
# Kopiujemy wrapper Mavena i pom.xml
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# >>> DODAJ TĘ LINIĘ, ABY NADAĆ UPRAWNIENIA DO WYKONANIA <<<
RUN chmod +x ./mvnw

# Pobieramy zależności Mavena - to przyspiesza kolejne buildy, jeśli zależności się nie zmieniły
# Używamy ./mvnw, aby korzystać z wrappera z projektu
RUN ./mvnw dependency:go-offline -B

# Kopiujemy resztę kodu źródłowego aplikacji
COPY src ./src

# Budujemy aplikację (pakujemy do JARa), pomijając testy
# Używamy ./mvnw
RUN ./mvnw package -DskipTests

# Etap 2: Tworzenie finalnego obrazu uruchomieniowego
# Używamy lekkiego obrazu JRE 17 (Temurin) opartego na Ubuntu Jammy
FROM eclipse-temurin:17-jre-jammy

# Ustawiamy katalog roboczy w kontenerze
WORKDIR /app

# Kopiujemy zbudowany plik JAR z etapu 'builder'
# Upewnij się, że nazwa pliku JAR jest poprawna (zgodna z Twoim pom.xml)
COPY --from=builder /app/target/animood_backend-0.0.1-SNAPSHOT.jar app.jar

# Ustawiamy zmienną środowiskową PORT, aby aplikacja Spring Boot jej używała.
# Render i tak przekaże swój PORT, ale to dobra praktyka.
# Spring Boot domyślnie nasłuchuje na 8080, jeśli $PORT nie jest ustawiony inaczej.
ENV PORT 8080

# Informujemy Docker, że aplikacja w kontenerze będzie nasłuchiwać na porcie 8080
EXPOSE 8080

# Polecenie uruchamiające aplikację przy starcie kontenera
ENTRYPOINT ["java", "-jar", "app.jar"]