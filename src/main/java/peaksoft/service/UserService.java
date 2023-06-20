package peaksoft.service;

import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.pagination.PaginationResponseUser;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.UserResponse;

public interface UserService {
    PaginationResponseUser findAll(int pageSize, int currentPage);
    SimpleResponse saveUser(UserRequest userRequest);
    UserResponse findById(Long id);
    SimpleResponse update(Long id,UserRequest userRequest);
    SimpleResponse deleteById(Long id);


}
