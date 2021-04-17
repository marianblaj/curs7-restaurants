package ro.fasttrackit.restaurants.controller;

import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.restaurants.entity.Restaurant;
import ro.fasttrackit.restaurants.model.RestaurantFilters;
import ro.fasttrackit.restaurants.service.CollectionResponse;
import ro.fasttrackit.restaurants.service.PageInfo;
import ro.fasttrackit.restaurants.service.RestaurantService;

import java.util.Optional;

import static ro.fasttrackit.restaurants.service.PageInfo.builder;


@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService service;

    @GetMapping
    CollectionResponse<Restaurant> getAll(
            RestaurantFilters filters,
            Pageable pageable) {
        Page<Restaurant> restaurantPage = service.getAll(filters, pageable);
        return CollectionResponse.<Restaurant>builder()
                .content(restaurantPage.getContent())
                .pageInfo(builder()
                        .totalPages(restaurantPage.getTotalPages())
                        .totalElements(restaurantPage.getNumberOfElements())
                        .crtPage(pageable.getPageNumber())
                        .pageSize(pageable.getPageSize())
                        .build())
                .build();
    }

    @GetMapping("/{restaurantId}")
    Optional<Restaurant> getRestaurantId(@PathVariable long restaurantId){
        return service.getRestaurantId(restaurantId);
    }

    @PostMapping
    Restaurant addRestaurant(@RequestBody Restaurant newRestaurant) {
        return service.addRestaurant(newRestaurant);
    }

    @PutMapping("/{restaurantId}")
    Restaurant replaceRestaurant(@RequestBody Restaurant newRestaurant, @PathVariable long restaurantId) {
        return service.replaceRestaurant(restaurantId, newRestaurant);
    }

    @PatchMapping("/{restaurantId}")
    Restaurant patchRestaurant(@RequestBody JsonPatch patch, @PathVariable long restaurantId) {
        return service.patchRestaurant(restaurantId, patch);
    }

    @DeleteMapping("/{restaurantId}")
    void deleteRestaurant(@PathVariable long restaurantId){
         service.deleteRestaurant(restaurantId);
    }

}
