package peaksoft.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.pagination.PaginationResponseUser;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.UserResponse;
import peaksoft.service.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserApi {

    private final UserService service;

    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER','CHEF')")
    @GetMapping
    PaginationResponseUser getAllUsers(@RequestParam int pageSize,int currentPage){
        return service.getAllUsers(currentPage,pageSize);
    }

    @PostMapping("/register")
    public SimpleResponse registerUser(@RequestBody @Valid UserRequest userRequest){
        return service.register(userRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/acceptOrReject")
    public  SimpleResponse AcceptOrReject(@RequestParam Long userId,@RequestParam Long restaurantId, @RequestParam String word){
        return service.acceptUser(userId, restaurantId, word);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update/{id}")
    public SimpleResponse updateUser(@PathVariable Long id,@RequestBody UserRequest userRequest){
        return service.updateUserById(id, userRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER','CHEF')")
    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id){
        return service.getUserById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER','CHEF')")
    @DeleteMapping("/{id}")
    public SimpleResponse deleteUserById(@PathVariable Long id){
        return service.deleteUserById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/saveByAdmin")
    public SimpleResponse saveUserByAdmin(@RequestParam Long id,@RequestBody @Valid UserRequest userRequest){
        return service.saveUser(id,userRequest);
    }
}