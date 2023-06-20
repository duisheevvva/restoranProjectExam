package peaksoft.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ChequeRequest {
    private Long waiterId;
    @NotEmpty(message = "fill in the field")
    private int priceAverage;
    @NotEmpty(message = "fill in the field")
    private List<String> menuItemNames;
}
