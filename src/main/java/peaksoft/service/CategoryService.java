package peaksoft.service;

import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.dto.response.pagination.PaginationResponseCategory;
import peaksoft.dto.response.pagination.PaginationResponseUser;
import peaksoft.dto.response.SimpleResponse;

public interface CategoryService {
    PaginationResponseCategory findAll(int pageSize, int currentPage);
    SimpleResponse saveCategory(CategoryRequest categoryRequest);
    CategoryResponse findById(Long id);
    SimpleResponse update(Long id,CategoryRequest categoryRequest);
    SimpleResponse deleteById(Long id);

}
