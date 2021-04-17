package ro.fasttrackit.restaurants.countries;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("Suedia")
public class Suedia implements Countries{
    @Override
    public List<String> readCity() {
        return List.of(
                "Stockholm",
                "Goteborg",
                "Malmo",
                "Uppsala",
                "Orebro"
        );
    }
}
