import tools.jackson.databind.ObjectMapper;

import javax.xml.crypto.Data;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();

        List<Director> directors = new ArrayList<>();

        directors = mapper
                .readerForListOf(Director.class)
                .readValue(
                        Director.class.getResourceAsStream("movies.json")
                );

        System.out.println(directors);

        List<Movie> movies = new ArrayList<>();
        directors.forEach(d -> movies.addAll(d.getMovies()));

        // Drama Movies

        System.out.println();
        System.out.println("Drama-movies:");
        movies.stream()
                .filter(m -> m.getGenres().contains("Drama"))
                .forEach(System.out::println);

        // Longest Movie

        System.out.println();
        System.out.println("Longest Movie: ");
        System.out.println(movies.stream()
                .max(Comparator.comparing(Movie::getDuration))
                .get());

        // Average Runtime

        System.out.println();
        System.out.println("Average Movie length: ");
        System.out.println(movies.stream()
                .collect(Collectors.averagingInt(Movie::getDuration)));

        // All Genres but once

        System.out.println();
        System.out.println("All Genres: ");
        Set<String> distinctGenres = new LinkedHashSet<>();
        movies.forEach(m -> distinctGenres.addAll(m.getGenres()));

        System.out.println(distinctGenres);

        // Director with the most Movies

        System.out.println();
        System.out.println("Director with the most Movies: ");

        System.out.println(directors.stream()
                .max(Comparator.comparing((d1) -> d1.getMovies().size()))
                .get());

        // Classic genres

        System.out.println();
        directors.forEach(d -> d.getMovies().stream()
                 .filter(m -> m.getReleaseDate().isBefore(LocalDate.of(2015, 1, 1)))
                 .forEach(m -> m.getGenres().add("Classic"))) ;

        movies.clear();
        directors.forEach(d -> movies.addAll(d.getMovies()));


        // Change duration of dune

        directors.forEach(d -> {
            d.getMovies().stream()
                    .filter(m -> m.getTitle().strip().equalsIgnoreCase("dune"))
                    .findFirst()
                    .ifPresent(m -> m.setDuration(156));
        });

        // Change Studio of Arrival

        directors.forEach(d -> {
            d.getMovies().stream()
                    .filter(m -> m.getTitle().strip().equalsIgnoreCase("arrival"))
                    .findFirst()
                    .ifPresent(m -> m.setStudio(Studio.builder()
                                    .name("New Film Studio")
                                    .country("Canada")
                                    .build()));
        });
        // Add Oppenheimer

        Movie movie = Movie.builder()
                .title("Oppenheimer")
                .duration(100)
                .genres(Set.of("Drama", "History"))
                .studio(Studio.builder()
                        .name("Universal Pictures")
                        .country("USA")
                        .build())
                .releaseDate(LocalDate.of(2023, 7, 21))
                .director(directors.stream().filter(d -> d.getFirstname().strip().equalsIgnoreCase("Christopher") && d.getLastname().strip().equalsIgnoreCase("Nolan"))
                        .findFirst()
                        .orElse(Director.builder()
                                .firstname("Christopher")
                                .lastname("Nolan")
                                .movies(new ArrayList<>())
                                .build()))
                .build();

        directors.stream().filter(d -> d.getFirstname().strip().equalsIgnoreCase("Christopher") && d.getLastname().strip().equalsIgnoreCase("Nolan"))
                .findFirst()
                .ifPresent(d -> d.getMovies().add(movie));

        mapper.writerWithDefaultPrettyPrinter()
                .writeValue(Path.of(System.getProperty("user.dir"), "src", "main", "resources", "updated.json"),
                        directors
                );
    }
}
