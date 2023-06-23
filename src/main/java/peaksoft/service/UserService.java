package peaksoft.service;

import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.pagination.PaginationResponseUser;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.UserResponse;

public interface UserService {
    PaginationResponseUser getAllUsers(int pageSize,int currentPage);
    SimpleResponse register (UserRequest userRequest);
    SimpleResponse acceptUser(Long restaurantId, Long userId,String word);
    SimpleResponse updateUserById(Long id, UserRequest userRequest);
    SimpleResponse deleteUserById(Long id);
    UserResponse getUserById(Long id);
    SimpleResponse saveUser(Long restaurantId,UserRequest userRequest);


}
