package peaksoft.service;

import peaksoft.dto.request.SubcategoryRequest;
import peaksoft.dto.response.pagination.PaginationResponseSubCategory;
import peaksoft.dto.response.pagination.PaginationResponseUser;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.SubcategoryResponse;

public interface SubcategoryService {

    PaginationResponseSubCategory findAll(Long id, int pageSize, int currentPage);
    SimpleResponse saveSubcategory(SubcategoryRequest subcategoryRequest);
    SubcategoryResponse  findById(Long id);
    SimpleResponse update(Long id,SubcategoryRequest subcategoryRequest);
    SimpleResponse deleteById(Long id);
}
