package peaksoft.dto.request;


import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import peaksoft.validator.Password;

@Getter
@Setter
@AllArgsConstructor
public class SignInRequest {
    @NotEmpty(message = "fill in the field")
    private String email;
    @NotEmpty(message = "fill in the field")
    @Password
    private String password;
}
