package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.ChequeRequest;
import peaksoft.dto.request.RestaurantRequestOfDay;
import peaksoft.dto.request.WaiterRequest;
import peaksoft.dto.response.ChequeResponse;
import peaksoft.dto.response.RestaurantResponseOfDay;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.WaiterResponseOfDay;
import peaksoft.service.ChequeService;


import java.util.List;


@RestController
@RequestMapping("/cheques")
@RequiredArgsConstructor
public class ChequeApi {
    private final ChequeService chequeService;
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    public ChequeResponse save(@RequestBody ChequeRequest request){
        return chequeService.save(request);
    }
    @PatchMapping("/totalWaiter")
    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    public WaiterResponseOfDay totalWaiter(@RequestBody WaiterRequest request){
        return chequeService.totalPriceWalter(request);
    }

    @GetMapping ("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER','CHEF')")
    public ChequeResponse getById(@PathVariable Long id){
        return chequeService.getById(id);
    }
    @GetMapping("/totalRestaurant")
    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    public RestaurantResponseOfDay totalRestor(@RequestBody RestaurantRequestOfDay request){
        return chequeService.totalPriceRestaurant(request);
    }

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    public List<ChequeResponse>getAll(){
        return chequeService.getAll();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ChequeResponse update(@PathVariable Long id,@RequestBody ChequeRequest request){
        return chequeService.update(id, request);
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public SimpleResponse deleteCategoryById(@PathVariable Long id) {
        return chequeService.delete(id);
    }
}

