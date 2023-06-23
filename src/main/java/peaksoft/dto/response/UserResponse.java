package peaksoft.dto.response;

import lombok.Builder;
import lombok.Data;
import peaksoft.enums.Role;

import java.time.LocalDate;


@Data
@Builder
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String email;
    private String password;
    private String phoneNumber;
    private Role role;
    private int experience;

    public UserResponse(Long id, String firstName, String lastName, LocalDate dateOfBirth, String email, String password, String phoneNumber, Role role, int experience) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.experience = experience;
    }
}
