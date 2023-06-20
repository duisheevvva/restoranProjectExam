package peaksoft.dto.response.pagination;

import lombok.Builder;
import peaksoft.dto.response.SubcategoryResponse;

import java.util.List;



@Builder
public record PaginationResponseSubCategory<page>(
    List<SubcategoryResponse> subcategoryResponseList,
    int size,
    int page

    ){

}

