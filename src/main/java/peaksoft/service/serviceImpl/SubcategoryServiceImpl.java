package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.SubcategoryRequest;
import peaksoft.dto.response.*;
import peaksoft.dto.response.pagination.PaginationResponseSubCategory;
import peaksoft.entity.Category;
import peaksoft.entity.Subcategory;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repository.CategoryRepository;
import peaksoft.repository.SubcategoryRepository;
import peaksoft.service.SubcategoryService;

@Service
@Transactional
@RequiredArgsConstructor
public class SubcategoryServiceImpl implements SubcategoryService {

    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;

    @Override
    public PaginationResponseSubCategory findAll(Long id, int pageSize, int currentPage) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        Page<SubcategoryResponse> allSubCategory = subcategoryRepository.findSubcategoriesByCategoryId(id, pageable);
        return PaginationResponseSubCategory
                .builder()
                .subcategoryResponseList(allSubCategory.getContent())
                .page(allSubCategory.getNumber() + 1)
                .size(allSubCategory.getTotalPages())
                .build();
    }

    @Override
    public SimpleResponse saveSubcategory(SubcategoryRequest subcategoryRequest) {
        Category category = categoryRepository.findById(subcategoryRequest.getCategoryId())
                .orElseThrow(() -> new NotFoundException("User with id " + subcategoryRequest.getCategoryId() + "is not found!"));
        Subcategory subcategory1 = new Subcategory();
        subcategory1.setName(subcategoryRequest.getName());
//        category.setSubcategories(subcategory1.getCategory().getSubcategories());
        category.addSubCategory(subcategory1);
        subcategory1.setCategory(category);
        categoryRepository.save(category);
        subcategoryRepository.save(subcategory1);
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("SubCategory with name : %s  " +
                                "successfully saved",
                        subcategoryRequest.getName()))
                .build();
    }

    @Override
    public SubcategoryResponse findById(Long id) {
        return subcategoryRepository.finId(id).orElseThrow(
                () -> new NotFoundException("Subcategory with id : " + id + "is not found!"));
    }

    @Override
    public SimpleResponse update(Long id, SubcategoryRequest subcategoryRequest) {
        Subcategory oldSubcategory = subcategoryRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Subcategory with id : " + id + "is not found!"));
        Category category = categoryRepository.findById(subcategoryRequest.getCategoryId()).orElseThrow(
                () -> new NotFoundException("Category with id " + subcategoryRequest.getCategoryId() + "is not found!"));
        oldSubcategory.setName(subcategoryRequest.getName());
        oldSubcategory.setCategory(category);
        subcategoryRepository.save(oldSubcategory);
        return SimpleResponse.
                builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("SubCategory with name : %s  " +
                                "successfully update",
                        subcategoryRequest.getName()))
                .build();
    }

    @Override
    public SimpleResponse deleteById(Long id) {
        if (!subcategoryRepository.existsById(id)) {
            throw new NotFoundException("Subcategory with id : " + id + "is not found");
        }
        subcategoryRepository.deleteById(id);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK)
                .message(String.format("Subcategory with id : %s is deleted!", id)).build();
    }
}
