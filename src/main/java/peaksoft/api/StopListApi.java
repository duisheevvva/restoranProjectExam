package peaksoft.api;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.StopListRequest;
import peaksoft.dto.response.pagination.PaginationResponseStopList;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.StopListResponse;
import peaksoft.service.StopListService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stopLists")
public class StopListApi {
    private final StopListService stopListService;

    @GetMapping
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public PaginationResponseStopList findAll(int pageSize, int currentPage) {
        return stopListService.findAll(pageSize, currentPage);
    }

    @PostMapping("/{menuId}")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public StopListResponse saveStopList(@PathVariable Long menuId, @RequestBody StopListRequest stopListRequest) {
        return stopListService.saveStopList(menuId, stopListRequest);
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public StopListResponse findById(@PathVariable Long id) {
        return findById(id);
    }

    @PutMapping("/{id}")
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public StopListResponse updateStopListById(@PathVariable Long id,
                                               @RequestBody StopListRequest stopListRequest) {
        return stopListService.update(id, stopListRequest);
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public SimpleResponse deleteStopListById(@PathVariable Long id) {
        return stopListService.deleteById(id);
    }

}
