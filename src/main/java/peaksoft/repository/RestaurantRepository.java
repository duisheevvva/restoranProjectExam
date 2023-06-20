package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.RestaurantResponse;
import peaksoft.entity.Restaurant;
import peaksoft.enums.RestType;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {
//    @Query("select  new peaksoft.dto.response.RestaurantResponse(r.id,r.name,r.location,r.restType,r.numberOfEmployees,r.service) from Restaurant r")
//    List<RestaurantResponse> findAllRestaurant();
    @Query("select  new  peaksoft.dto.response.RestaurantResponse(r.id,r.name,r.location,r.restType,r.numberOfEmployees,r.service) from Restaurant r where r.id=:id")
    Optional<RestaurantResponse> getRestaurantById(Long id);
    Optional<Restaurant> findRestaurantByName(String name);

    boolean existsByName(String name);
}
