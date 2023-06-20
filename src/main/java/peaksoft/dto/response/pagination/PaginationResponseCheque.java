package peaksoft.dto.response.pagination;

import lombok.Builder;

import java.util.List;

@Builder
public record PaginationResponseCheque(
        List<ChequeResponse> userResponseList,
        int size,
        int page
) {
}

