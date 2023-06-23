package peaksoft.service;

import peaksoft.dto.request.StopListRequest;
import peaksoft.dto.response.StopListResponse;
import peaksoft.dto.response.pagination.PaginationResponseStopList;
import peaksoft.dto.response.pagination.PaginationResponseUser;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entity.StopList;

public interface StopListService {
    PaginationResponseStopList findAll(int pageSize, int currentPage);
    StopListResponse saveStopList(Long menuId, StopListRequest request);
    StopList findById(Long id);
    SimpleResponse update(Long id,StopListRequest stopListRequest);
    SimpleResponse deleteById(Long id);
}
