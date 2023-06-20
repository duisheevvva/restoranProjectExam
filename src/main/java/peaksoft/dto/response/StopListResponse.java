package peaksoft.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
public class StopListResponse {
    private Long id;
    private String reason;
    private String menuItemName;
    private LocalDate date;

    public StopListResponse(Long id, String reason, String menuItemName, LocalDate date) {
        this.id = id;
        this.reason = reason;
        this.menuItemName = menuItemName;
        this.date = date;
    }
}
