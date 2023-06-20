package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.RestaurantRequest;
import peaksoft.dto.response.RestaurantResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entity.Restaurant;
import peaksoft.entity.User;
import peaksoft.exceptions.AlreadyExistException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.RestaurantService;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    @Override
    public SimpleResponse saveRestaurant(RestaurantRequest restaurantRequest) {
        if (!restaurantRepository.findAll().isEmpty()) {
            throw new AlreadyExistException("You mast save only 1 Restaurant");
        }
        if (restaurantRepository.existsByName(restaurantRequest.getName())) {
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message(String.format("Restaurant with name : %s already exists", restaurantRequest.getName()))
                    .build();
        }
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getUserByEmail(email)
                .orElseThrow(() -> new NotFoundException("User with email: %s not found".formatted(email)));
        Restaurant restaurant = Restaurant.builder()
                .name(restaurantRequest.getName())
                .location(restaurantRequest.getLocation())
                .restType(restaurantRequest.getRestType())
                .service(restaurantRequest.getService())
                .build();
        restaurantRepository.save(restaurant);
        user.setRestaurant(restaurant);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Restaurant with name: %s successfully saved!",
                        restaurantRequest.getName()))
                .build();
    }

    @Override
    public RestaurantResponse findById(Long id) {
        return restaurantRepository.getRestaurantById(id).orElseThrow(
                () -> new NotFoundException("Restaurant with id : " + id + "is not found!"));
    }

    @Override
    public SimpleResponse update(Long id, RestaurantRequest restaurantRequest) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Restaurant with id " + id + "is not found!"));
        restaurant.setName(restaurantRequest.getName());
        restaurant.setLocation(restaurantRequest.getLocation());
        restaurant.setRestType(restaurantRequest.getRestType());
        restaurant.setService(restaurantRequest.getService());
        restaurantRepository.save(restaurant);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Restaurant with id: " + id + " is updated"))
                .build();
    }

    @Override
    public SimpleResponse deleteById(Long id) {
        if (!restaurantRepository.existsById(id)) {
            throw new NotFoundException("Restaurant with id : " + id + "is not found");
        }
        restaurantRepository.deleteById(id);
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Restaurant with id : %s is deleted!", id))
                .build();
    }
}
