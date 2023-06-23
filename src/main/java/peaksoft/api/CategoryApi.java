package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.dto.response.pagination.PaginationResponseCategory;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.service.CategoryService;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryApi {
    private final CategoryService categoryService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public PaginationResponseCategory getAllCategories(int pageSize, int currentPage) {
        return categoryService.findAll(pageSize,currentPage);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse saveCategory(@RequestBody CategoryRequest categoryRequest) {
        return categoryService.saveCategory(categoryRequest);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public CategoryResponse findById(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public SimpleResponse updateCategoryById(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest) {
        return categoryService.update(id, categoryRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public SimpleResponse deleteCategoryById(@PathVariable Long id) {
        return categoryService.deleteById(id);
    }

}
