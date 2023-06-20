package peaksoft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.pagination.PaginationResponseUser;
import peaksoft.dto.response.SubcategoryResponse;
import peaksoft.entity.Subcategory;

import java.util.Optional;

public interface SubcategoryRepository extends JpaRepository<Subcategory,Long> {
    Page<SubcategoryResponse> findSubcategoriesByCategoryId(Long id, Pageable pageable);
    @Query("select new peaksoft.dto.response.SubcategoryResponse(s.id,s.name) from Subcategory s where s.id = :id" )
    Optional<SubcategoryResponse> finId(Long id);
}
