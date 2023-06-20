package peaksoft.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class StopListRequest {
    @NotEmpty(message = "fill in the field")
    private String reason;
    @NotEmpty(message = "fill in the field")
    private String menuItemName;
    private LocalDate date;

}
