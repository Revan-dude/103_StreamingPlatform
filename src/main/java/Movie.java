import CustomSerializers.CustomGenreDeserializer;
import CustomSerializers.CustomGenreSerializer;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Movie {
    private Studio studio;

    @JsonBackReference
    private Director director;

    @JsonAlias({"name", "title"})
    private String title;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate releaseDate;

    private int duration;

    @JsonSerialize(using = CustomGenreSerializer.class)
    @JsonDeserialize(using = CustomGenreDeserializer.class)
    private Set<String> genres = new LinkedHashSet<>();

    @Override
    public String toString() {
        return String.format("%s | %s | %d min | [%s]",
                title, releaseDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), duration, String.join(",", genres)
                );
    }


}
