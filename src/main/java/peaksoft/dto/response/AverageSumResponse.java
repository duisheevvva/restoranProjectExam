package peaksoft.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class AverageSumResponse {

    private int sum;

}
