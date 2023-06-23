package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.UserResponse;
import peaksoft.dto.response.pagination.PaginationResponseUser;
import peaksoft.entity.Restaurant;
import peaksoft.entity.User;
import peaksoft.enums.Role;
import peaksoft.exceptions.BadCredentialException;
import peaksoft.exceptions.BadRequestException;
import peaksoft.exceptions.NoSuchElementException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.UserService;

import java.time.LocalDate;
import java.time.Period;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final RestaurantRepository restaurantRepository;
    private final PasswordEncoder passwordEncoder;
    int counter = 0;
    private User getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = repository.getUserByEmail(name).orElseThrow(() -> new NotFoundException("User with email: " + name + " us bit found!"));
        return user;
    }

    @Override
    public PaginationResponseUser getAllUsers(int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        Page<UserResponse> allUsers = repository.getAllUsers(pageable);
        return PaginationResponseUser.builder()
                .userResponseList(allUsers.getContent())
                .page(allUsers.getNumber() + 1)
                .size(allUsers.getTotalPages())
                .build();
    }

    @Override
    public SimpleResponse register(UserRequest userRequest) {
        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        int age = Period.between(userRequest.getDateOfBirth(), LocalDate.now()).getYears();
        if (userRequest.getRole().equals(Role.CHEF)){
            if (age>=25 && age<=45){
                user.setDateOfBirth(userRequest.getDateOfBirth());
            } else throw new BadCredentialException("You can not apply for this job because of your age...");

        }else if (userRequest.getRole().equals(Role.WAITER)){
            if (age<30 && age>18){
                user.setDateOfBirth(userRequest.getDateOfBirth());

            }else throw new BadRequestException("You can not apply for this job because your age...");
        }
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setRole(userRequest.getRole());
        if (userRequest.getRole().equals(Role.CHEF)) {
            if (userRequest.getExperience() >= 2) {
                user.setExperience(userRequest.getExperience());
            } else throw new BadRequestException("Your experience does not enough for chef vocation");
        } else if (userRequest.getRole().equals(Role.WAITER)) {
            if (userRequest.getExperience() >= 1) {
                user.setExperience(userRequest.getExperience());
            } else throw new BadRequestException("Your experience does not enough for waiter vocation");
        }
        Restaurant restaurant = restaurantRepository.findById(1L).orElseThrow(() -> new NoSuchElementException("Restaurant does not exist..."));

        if (restaurant.getUsers().size()<=15){
            repository.save(user);
        }else throw new BadCredentialException("There is no more vocation!");

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("You successfully applied for the job..."+restaurant.getUsers().size()))
                .build();
    }

    @Override
    public SimpleResponse acceptUser(Long restaurantId, Long userId, String word) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new NotFoundException("Restaurant with id: " + restaurantId + " is not found!"));
        User user = repository.findById(userId).orElseThrow(() -> new NotFoundException("User with id: " + userId + " is not found!"));
        if (word.equalsIgnoreCase("accepted")) {
            restaurant.getUsers().add(user);
            restaurantRepository.save(restaurant);
            user.setRestaurant(restaurant);
            repository.save(user);
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("Successfully accepted!")
                    .build();
        }
        if (word.equalsIgnoreCase("reject")) {
            repository.delete(user);
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("Successfully rejected")
                    .build();
        }
        return null;
    }

    @Override
    public SimpleResponse updateUserById(Long id, UserRequest userRequest) {
        User user = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id: " + id + " is not found!"));
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setDateOfBirth(userRequest.getDateOfBirth());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setRole(userRequest.getRole());
        user.setExperience(userRequest.getExperience());
        repository.save(user);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("successfully updated...")
                .build();
    }

    @Override
    public SimpleResponse deleteUserById(Long id) {
        User user = getAuthentication();
        User user1 = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id: " + id + " is not found!"));
        if (user.getRole().equals(Role.ADMIN)) {
            if (repository.existsById(id)) {
                repository.deleteById(id);
                return SimpleResponse.builder()
                        .httpStatus(HttpStatus.OK)
                        .message("Successfully deleted")
                        .build();
            } else throw new NotFoundException("User with id:" + id + " is does not exist...");
        } else {
            if (user.equals(user1)) {
                if (repository.existsById(id)) {
                    repository.deleteById(id);
                    return SimpleResponse.builder()
                            .httpStatus(HttpStatus.OK)
                            .message("Successfully deleted")
                            .build();
                } else throw new NotFoundException("User with id:" + id + " is does not exist...");
            } else throw new BadCredentialException("You can not get user with id:" + user1.getId());
        }


    }

    @Override
    public UserResponse getUserById(Long id) {
        User user1 = getAuthentication();
        User user = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id: " + id + " is not found!"));

        if (user1.getRole().equals(Role.ADMIN)) {
            return UserResponse.builder()
                    .id(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .dateOfBirth(user.getDateOfBirth())
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .phoneNumber(user.getPhoneNumber())
                    .role(user.getRole())
                    .experience(user.getExperience())
                    .build();
        } else {
            if (user1.equals(user)) {
                return UserResponse.builder()
                        .id(user.getId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .dateOfBirth(user.getDateOfBirth())
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .phoneNumber(user.getPhoneNumber())
                        .role(user.getRole())
                        .experience(user.getExperience())
                        .build();
            } else throw new BadCredentialException("You can not get user with id:" + user.getId());
        }


    }

    @Override
    public SimpleResponse saveUser(Long restaurantId, UserRequest userRequest) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new NotFoundException("Restaurant with id: " + restaurantId + " is not found..."));
        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        int age = Period.between(userRequest.getDateOfBirth(), LocalDate.now()).getYears();
        if (userRequest.getRole().equals(Role.CHEF)){
            if (age>=25 && age<=45){
                user.setDateOfBirth(userRequest.getDateOfBirth());
            } else throw new BadCredentialException("You can not apply for this job because of your age...");

        }else if (userRequest.getRole().equals(Role.WAITER)){
            if (age<30 && age>18){
                user.setDateOfBirth(userRequest.getDateOfBirth());

            }else throw new BadRequestException("You can not apply for this job because your age...");
        }
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setRole(userRequest.getRole());
        user.setRestaurant(restaurant);
        if (userRequest.getRole().equals(Role.CHEF)) {
            if (userRequest.getExperience() >= 2) {
                user.setExperience(userRequest.getExperience());
            } else throw new BadRequestException("Your experience does not enough for chef vocation");
        } else if (userRequest.getRole().equals(Role.WAITER)) {
            if (userRequest.getExperience() >= 1) {
                user.setExperience(userRequest.getExperience());
            } else throw new BadRequestException("Your experience does not enough for waiter vocation");
        }

        if (restaurant.getUsers().size()<=15){
            repository.save(user);
        }else throw new BadCredentialException("There is no more vocation!");

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("You successfully applied for the job..."+restaurant.getUsers().size()))
                .build();
    }

}