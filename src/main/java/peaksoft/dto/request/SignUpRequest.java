package peaksoft.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import peaksoft.enums.Role;
import peaksoft.validator.Password;

@Getter
@Setter
@AllArgsConstructor
public class SignUpRequest {
//    @NotEmpty(message = "fill in the field")
    private String firstName;
//    @NotEmpty(message = "fill in the field")
    private String lastName;
//    @NotEmpty(message = "fill in the field")
    private String email;
    @Password
//    @NotEmpty(message = "fill in the field")
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

}
