package peaksoft.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StopListRequest {
    @NotEmpty(message = "fill in the field")
    private String reason;
//    @NotEmpty(message = "fill in the field")
//    private String menuItemName;
    private LocalDate date;

    public StopListRequest(String reason, LocalDate date) {
        this.reason = reason;
        this.date = date;
    }
}
