package ro.fasttrackit.restaurants.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.fasttrackit.restaurants.countries.Countries;
import ro.fasttrackit.restaurants.entity.Restaurant;
import ro.fasttrackit.restaurants.exception.ValidationException;
import ro.fasttrackit.restaurants.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.Optional;

import static java.util.Optional.empty;

@Component
@RequiredArgsConstructor
public class RestaurantValidator {

    private final Countries countries;

    private final RestaurantRepository repo;

    public void validateNewThrow(Restaurant newRestaurant) {
        validate(newRestaurant, true)
                .ifPresent(ex -> {
                    throw ex;
                });
    }

    private Optional<ValidationException> validate(Restaurant restaurant, boolean newEntity) {
        if (restaurant.getName() == null)
            return Optional.of(new ValidationException("Name cannot be null"));
        else if (newEntity && repo.existsByName(restaurant.getName()))
            return Optional.of(new ValidationException("Name cannot be duplicate"));
        else if (!newEntity && repo.existsByNameAndIdNot(restaurant.getName(), restaurant.getId()))
            return Optional.of(new ValidationException("Name cannot be duplicate"));
        else if (!countries.readCity().contains(restaurant.getCity()))
            return Optional.of(new ValidationException("Name of the city is not allowed."));
        else if (LocalDate.now().compareTo(restaurant.getSince()) < 0)
            return Optional.of(new ValidationException("Date since cannot be in the future."));
        else
            return empty();

    }

    public void validateReplaceThrow(long restaurantId, Restaurant newRestaurant) {
        exists(restaurantId)
                .or(() -> validate(newRestaurant, false))
                .ifPresent(ex -> {
                    throw ex;
                });
    }

    private Optional<ValidationException> exists(long restaurantId) {
        return repo.existsById(restaurantId)
                ? empty()
                : Optional.of(new ValidationException("Product with id " + restaurantId + " does not exist."));
    }

    public void validateExistsOrThrow(long restaurantId) {
        exists(restaurantId).ifPresent(ex -> {
            throw ex;
        });
    }

}
