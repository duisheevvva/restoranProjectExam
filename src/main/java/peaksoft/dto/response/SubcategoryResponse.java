package peaksoft.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubcategoryResponse {
    private Long id;
    private String name;

    public SubcategoryResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
