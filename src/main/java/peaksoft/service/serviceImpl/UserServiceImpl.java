package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.pagination.PaginationResponseUser;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.UserResponse;
import peaksoft.entity.Restaurant;
import peaksoft.entity.User;
import peaksoft.enums.Role;
import peaksoft.exceptions.AlreadyExistException;
import peaksoft.exceptions.BadRequestException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.UserService;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public PaginationResponseUser findAll(int pageSize, int currentPage) {
        Pageable pageable= PageRequest.of(currentPage-1,pageSize);
        Page<UserResponse>allUsers=userRepository.findAllUsers(pageable);
        return PaginationResponseUser
                .builder()
                .userResponseList(allUsers.getContent())
                .page(allUsers.getNumber()+1)
                .size(allUsers.getTotalPages())
                .build();
    }

    @Override
    public SimpleResponse saveUser(UserRequest userRequest) {
        Restaurant restaurant = restaurantRepository.findById(userRequest.getRestaurantId()).orElseThrow(
                () -> new NotFoundException("Restaurant with id : " + userRequest.getRestaurantId() + "is not found!"));
        check(userRequest);
        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setDateOfBirth(userRequest.getDateOfBirth());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setRole(userRequest.getRole());
        user.setExperience(userRequest.getExperience());
        int count = restaurant.getUsers().size();
        if (count > 15) {
            throw new AlreadyExistException("No vacancy");
        } else if (userRequest.getRole().equals(Role.ADMIN)) {
            throw new AlreadyExistException("Already have an admin");
        } else {
            if (userRequest.getRestaurantId() != null) {
                Restaurant restaurant1 = restaurantRepository.findById(userRequest.getRestaurantId()).orElseThrow(() -> new NoSuchElementException("Restaurant with id: %s not found".formatted(userRequest.getRestaurantId())));
                user.setRestaurant(restaurant1);
                restaurant1.getUsers().add(user);
                userRepository.save(user);
            } else {
                throw new BadRequestException("Restaurant id is null");
            }
        }
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Employee with fullName : %s job title: %s " +
                                "successfully saved",
                        user.getFirstName()
                                .concat(user.getLastName()),
                        user.getRole())).build();
    }


    @Override
    public UserResponse findById(Long id) {
        return userRepository.getUserById(id).orElseThrow(
                () -> new NotFoundException("User with id : " + id + "is not found!"));
    }

    @Override
    public SimpleResponse update(Long id, UserRequest userRequest) {
        check(userRequest);
        User oldUser = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("User with id " + id + "is not found!"));
        oldUser.setFirstName(userRequest.getFirstName());
        oldUser.setLastName(userRequest.getLastName());
        oldUser.setDateOfBirth(userRequest.getDateOfBirth());
        oldUser.setEmail(userRequest.getEmail());
        oldUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        oldUser.setPhoneNumber(userRequest.getPhoneNumber());
        oldUser.setRole(userRequest.getRole());
        oldUser.setExperience(userRequest.getExperience());
        userRepository.save(oldUser);
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("User with name : %s " + "successfully update", userRequest.getFirstName()))
                .build();
    }

    @Override
    public SimpleResponse deleteById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id : " + id + "is not found"));
        user.getChequeList()
                .forEach(cheque -> cheque.getMenuItems()
                        .forEach(menuItem -> menuItem.setChequeList(null)));
        userRepository.delete(user);
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("User with id : %s is deleted!", id))
                .build();
    }


    public void check(UserRequest request) {
        Boolean existsPh = userRepository.existsByPhoneNumber(request.getPhoneNumber());
        if (existsPh) {
            throw new AlreadyExistException("User with phone number: " + request.getPhoneNumber() + " is already exist!");
        }
        if (!request.getPhoneNumber().startsWith("+996")) {
            throw new BadRequestException("Phone number should starts with +996");
        }
        int year = LocalDate.now().minusYears(request.getDateOfBirth().getYear()).getYear();
        if (request.getRole().equals(Role.CHEF)) {
            if (year <= 25 || year >= 45 && request.getExperience() <= 2) {
                throw new BadRequestException("Chef's years old should be between 25-45 and experience>=2");
            }
        } else if (request.getRole().equals(Role.WAITER)) {
            if (year <= 18 || year >= 30 && request.getExperience() <= 1) {
                throw new BadRequestException("Waiter's years old should be between 18-30 and experience>=1");
            }
        }
    }
}
