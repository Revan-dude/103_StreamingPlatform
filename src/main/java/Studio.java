import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Studio {

    private String name;
    private String country;

    @Override
    public String toString() {
        return String.format("%s (%s)",
                name, country);
    }
}
