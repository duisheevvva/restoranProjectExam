package peaksoft.service;

import peaksoft.dto.request.ChequeRequest;
import peaksoft.dto.request.RestaurantRequestOfDay;
import peaksoft.dto.request.WaiterRequest;
import peaksoft.dto.response.ChequeResponse;
import peaksoft.dto.response.RestaurantResponseOfDay;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.WaiterResponseOfDay;

import java.util.List;

public interface ChequeService {
    ChequeResponse save(ChequeRequest request);
    WaiterResponseOfDay totalPriceWalter(WaiterRequest request);
    ChequeResponse update(Long id,ChequeRequest request);
    List<ChequeResponse>getAll();
    RestaurantResponseOfDay totalPriceRestaurant(RestaurantRequestOfDay request);
    SimpleResponse delete(Long id);
    ChequeResponse getById(Long id);
}
