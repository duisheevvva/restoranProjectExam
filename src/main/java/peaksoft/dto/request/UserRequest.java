package peaksoft.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import peaksoft.enums.Role;
import peaksoft.validator.Password;


import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class UserRequest {
    @NotEmpty(message = "fill in the field")
    private String firstName;
    @NotEmpty(message = "fill in the field")
    private String lastName;
    private LocalDate dateOfBirth;
    @Email
    @NotEmpty(message = "fill in the field")
    private String email;
    @NotEmpty(message = "fill in the field")
    @Password(message = "Incorrect password")
    private String password;

    private String phoneNumber;
    private Role role;
    @NotNull
    private int experience;

}
