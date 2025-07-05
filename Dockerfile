# --- ETAP 1: Budowanie aplikacji ---
# Używamy oficjalnego obrazu Maven z JDK 17 jako "builder"
FROM maven:3.9-eclipse-temurin-17 AS builder

# Ustawiamy katalog roboczy
WORKDIR /app

# Kopiujemy plik pom.xml, aby pobrać zależności
# Ta warstwa zostanie zbuforowana, jeśli pom.xml się nie zmieni
COPY pom.xml .
RUN mvn dependency:go-offline

# Kopiujemy resztę kodu źródłowego
COPY src ./src

# Budujemy aplikację, tworząc plik .jar
# Pomijamy testy, ponieważ zakładamy, że zostały już uruchomione
RUN mvn package -DskipTests

# --- ETAP 2: Tworzenie finalnego, lekkiego obrazu ---
# Używamy lekkiego obrazu zawierającego tylko środowisko uruchomieniowe Javy (JRE)
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Kopiujemy gotowy plik .jar z etapu "builder"
# Upewnij się, że nazwa pliku jar jest poprawna!
COPY --from=builder /app/target/*.jar app.jar

# Ustawiamy port, na którym nasłuchuje aplikacja
# Cloud Run automatycznie ustawi zmienną PORT
ENV PORT 8080
EXPOSE 8080

# Komenda uruchamiająca aplikację
ENTRYPOINT ["java", "-jar", "app.jar"]