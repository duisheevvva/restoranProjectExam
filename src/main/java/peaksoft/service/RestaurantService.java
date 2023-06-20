package peaksoft.service;

import peaksoft.dto.request.MenuItemRequest;
import peaksoft.dto.request.RestaurantRequest;
import peaksoft.dto.response.MenuItemResponse;
import peaksoft.dto.response.RestaurantResponse;
import peaksoft.dto.response.SimpleResponse;

import java.util.List;

public interface RestaurantService {
//    List<RestaurantResponse> findAll();
    SimpleResponse saveRestaurant(RestaurantRequest restaurantRequest);
    RestaurantResponse findById(Long id);
    SimpleResponse update(Long id,RestaurantRequest restaurantRequest);
    SimpleResponse deleteById(Long id);
}
