package peaksoft.dto.response.pagination;

import lombok.Builder;
import peaksoft.dto.response.StopListResponse;

import java.util.List;

@Builder
public record PaginationResponseStopList(
        List<StopListResponse> userResponseList,
        int size,
        int page


) {
}
