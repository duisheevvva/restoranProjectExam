package peaksoft.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequest {
    @NotEmpty(message = "fill in the field")
    private String name;

    public CategoryRequest(String name) {
        this.name = name;
    }

    public CategoryRequest() {
    }
}
