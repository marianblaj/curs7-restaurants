package ro.fasttrackit.restaurants.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ro.fasttrackit.restaurants.entity.Restaurant;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Page<Restaurant> findByStarsIn(List<Integer> stars, Pageable pageable);

    Page<Restaurant> findAll(Pageable pageable);
    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    Page<Restaurant> findByCity(String city, Pageable pageable);

}
