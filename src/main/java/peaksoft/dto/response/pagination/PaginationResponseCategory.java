package peaksoft.dto.response.pagination;

import lombok.Builder;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.dto.response.UserResponse;

import java.net.CacheResponse;
import java.util.List;

@Builder
public record PaginationResponseCategory(
        List<CategoryResponse> categoryResponseList,
        int size,
        int page
) {
}
