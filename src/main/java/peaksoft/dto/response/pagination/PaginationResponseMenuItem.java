package peaksoft.dto.response.pagination;

import lombok.Builder;
import peaksoft.dto.response.MenuItemResponse;


import java.util.List;

@Builder
public record PaginationResponseMenuItem(
        List<MenuItemResponse> manuItemList,
        int size,
        int page
) {

}
