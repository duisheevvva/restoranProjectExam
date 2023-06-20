package peaksoft.dto.request;


import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubcategoryRequest {
    private Long categoryId;
    @NotEmpty(message = "fill in the field")
    private String name;
}
