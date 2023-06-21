package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.pagination.PaginationResponseCategory;
import peaksoft.entity.Category;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repository.CategoryRepository;
import peaksoft.service.CategoryService;


@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public PaginationResponseCategory findAll(int pageSize, int currentPage) {
        Pageable pageable= PageRequest.of(currentPage-1,pageSize);
        Page<CategoryResponse> allCategory=categoryRepository.getAllCategories(pageable);
        return PaginationResponseCategory
                .builder()
                .categoryResponseList(allCategory.getContent())
                .page(allCategory.getNumber()+1)
                .size(allCategory.getTotalPages())
                .build();

    }

    @Override
    public SimpleResponse saveCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.getName());
        categoryRepository.save(category);
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("Category with name : %s " +
                                "successfully saved")
                .build();

    }

    @Override
    public CategoryResponse findById(Long id) {
        return categoryRepository.getCategoriesById(id).orElseThrow(
                () -> new NotFoundException("Category with id : " + id + "is not found!"));
    }

    @Override
    public SimpleResponse update(Long id, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Category with id " + id + "is not found!"));
        category.setName(categoryRequest.getName());
        categoryRepository.save(category);
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Category with name : %s " +
                                "successfully update",
                        categoryRequest.getName())).
                build();
    }

    @Override
    public SimpleResponse deleteById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new NotFoundException("Category with id : " + id + "is not found");
        }
        categoryRepository.deleteById(id);
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Category with id : %s is deleted!", id))
                .build();
    }
}
