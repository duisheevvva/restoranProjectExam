package peaksoft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.UserResponse;
import peaksoft.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getUserByEmail(String email);

    boolean existsByEmail(String email);

    @Query("select  new peaksoft.dto.response.UserResponse(u.id,u.firstName,u.lastName ,u.dateOfBirth," +
            "u.email,u.password,u.phoneNumber,u.role,u.experience ) from User u where u.id =:id")
    Optional<UserResponse> getUserById(Long id);

    @Query("select new peaksoft.dto.response.UserResponse(u.id,u.firstName,u.lastName,u.dateOfBirth,u.email,u.password,u.phoneNumber,u.role,u.experience) from User u")
    Page<UserResponse> getAllUsers(Pageable pageable);

    Boolean existsByPhoneNumber(String phoneNumber);
}