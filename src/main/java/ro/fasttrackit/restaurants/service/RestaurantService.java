package ro.fasttrackit.restaurants.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ro.fasttrackit.restaurants.exception.ResourceNotFoundException;
import ro.fasttrackit.restaurants.repository.RestaurantRepository;
import ro.fasttrackit.restaurants.entity.Restaurant;
import ro.fasttrackit.restaurants.model.RestaurantFilters;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository repo;
    private final RestaurantValidator validator;
    private final ObjectMapper mapper;


    public Page<Restaurant> getAll(RestaurantFilters filters, Pageable pageable) {
        if(!CollectionUtils.isEmpty(filters.getStars()))
            return repo.findByStarsIn(filters.getStars(), pageable);
        else  if(filters.getCity() != null)
            return repo.findByCity(filters.getCity(), pageable);
            else
                return repo.findAll(pageable);
    }

    public Restaurant addRestaurant(Restaurant newRestaurant) {
        validator.validateNewThrow(newRestaurant);

        return repo.save(newRestaurant);
    }

    public Restaurant replaceRestaurant(long restaurantId, Restaurant newRestaurant) {
        newRestaurant.setId(restaurantId);
        validator.validateReplaceThrow(restaurantId, newRestaurant);

        Restaurant dbRestaurant = repo.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Couldn't find the restaurant with the id " + restaurantId));
        copyRestaurant(newRestaurant, dbRestaurant);
        return repo.save(dbRestaurant);
    }

    private void copyRestaurant(Restaurant newRestaurant, Restaurant dbRestaurant) {
        dbRestaurant.setName(newRestaurant.getName());
        dbRestaurant.setStars(newRestaurant.getStars());
        dbRestaurant.setCity(newRestaurant.getCity());
        dbRestaurant.setSince(newRestaurant.getSince());
    }

    @SneakyThrows
    public Restaurant patchRestaurant(long restaurantId, JsonPatch patch) {
        validator.validateExistsOrThrow(restaurantId);
        Restaurant dbRestaurant = repo.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Couldn't find restaurant with id " + restaurantId));

        JsonNode patchedRestaurantJson = patch.apply(mapper.valueToTree(dbRestaurant));
        Restaurant patchedRestaurant = mapper.treeToValue(patchedRestaurantJson, Restaurant.class);
        return replaceRestaurant(restaurantId, patchedRestaurant);
    }

    public Optional<Restaurant> getRestaurantId(long restaurantId) {
        validator.validateExistsOrThrow(restaurantId);
       return repo.findById(restaurantId);

    }

    public void deleteRestaurant(long restaurantId) {
        validator.validateExistsOrThrow(restaurantId);
        repo.deleteById(restaurantId);
    }
}
