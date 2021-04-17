package ro.fasttrackit.restaurants.countries;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("Olanda")
public class Olanda implements Countries{
    @Override
    public List<String> readCity() {
        return List.of(
                "Amsterdam",
                "Rotterdam",
                "The Hague",
                "Utrecht",
                "Eindhoven"
        );
    }
}
