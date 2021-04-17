package ro.fasttrackit.restaurants.countries;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("Romania")
public class Romania implements Countries{

    @Override
    public List<String> readCity() {
        return List.of(
                "Bucharest",
                "Cluj-Napoca",
                "Timisoara",
                "Iasi",
                "Constanta"
        );
    }
}
