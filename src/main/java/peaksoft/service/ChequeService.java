package peaksoft.service;

import peaksoft.dto.request.ChequeRequest;
import peaksoft.dto.response.AverageSumResponse;
import peaksoft.dto.response.ChequeResponse;
import peaksoft.dto.response.ChequeTotalWaiterResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.pagination.PaginationResponseCheque;

import java.time.LocalDate;

public interface ChequeService {
    PaginationResponseCheque getAllCheques();
    SimpleResponse saveCheque(Long userId, ChequeRequest chequeRequest);
    SimpleResponse updateCheque(Long id, ChequeRequest chequeRequest);
//    ChequeResponse getChequeById(Long id);

    ChequeTotalWaiterResponse chequeTotalByWaiter(Long waiterId, LocalDate date);

    SimpleResponse deleteCheque(Long id);
    AverageSumResponse getAverageSum(LocalDate date);
    AverageSumResponse getAverageSumOfWaiter(Long waiterId, LocalDate dateTime);

}
