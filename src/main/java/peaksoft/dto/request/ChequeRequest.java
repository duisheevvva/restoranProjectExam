package peaksoft.dto.request;


import lombok.Data;

import java.util.List;

@Data
public class ChequeRequest {
    private List<Long> menuItemNames;
    public ChequeRequest() {
    }
}
