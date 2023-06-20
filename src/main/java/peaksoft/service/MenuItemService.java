package peaksoft.service;

import org.springframework.data.repository.query.Param;
import peaksoft.dto.request.MenuItemRequest;
import peaksoft.dto.response.MenuItemResponse;
import peaksoft.dto.response.pagination.PaginationResponseMenuItem;
import peaksoft.dto.response.pagination.PaginationResponseUser;
import peaksoft.dto.response.SearchResponse;
import peaksoft.dto.response.SimpleResponse;

import java.util.List;

public interface MenuItemService {
    PaginationResponseMenuItem findAllMenuItems(int pageSize, int currentPage);
    SimpleResponse saveMenuItem(MenuItemRequest menuItemRequest);
    MenuItemResponse findById(Long id);
    SimpleResponse update(Long id,MenuItemRequest menuItemRequest);
    SimpleResponse deleteById(Long id);
    List<SearchResponse> search(String keyword);

    List<MenuItemResponse> findAllMenuItemSortedByPriceAscAndDesc(String sort);
    List<MenuItemResponse> filter(@Param("isVegetarian") Boolean isVegetarian);
}
