package peaksoft.api;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.SubcategoryRequest;
import peaksoft.dto.response.pagination.PaginationResponseSubCategory;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.SubcategoryResponse;
import peaksoft.service.SubcategoryService;

@RestController
@RequestMapping("/subcategories")
@RequiredArgsConstructor
public class SubcategoryApi {

    private final SubcategoryService subcategoryService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public PaginationResponseSubCategory findAll(@RequestParam Long id, int pageSize, int currentPage){
        return subcategoryService.findAll(id, pageSize, currentPage);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse save(@RequestBody SubcategoryRequest subcategoryRequest){
        return subcategoryService.saveSubcategory(subcategoryRequest);
    }
    @GetMapping("/sub/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public SubcategoryResponse findById(@PathVariable Long id){
        return subcategoryService.findById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public  SimpleResponse update(@PathVariable Long id , @RequestBody SubcategoryRequest subcategory){
        return subcategoryService.update(id,subcategory);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public SimpleResponse deleteSubCategoryById(@PathVariable Long id){
        return subcategoryService.deleteById(id);
    }

}
