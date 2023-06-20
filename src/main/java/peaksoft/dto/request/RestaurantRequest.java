package peaksoft.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import peaksoft.enums.RestType;


@Getter
@Setter
public class RestaurantRequest {
    @NotEmpty(message = "fill in the field")
    private String name;
    @NotEmpty(message = "fill in the field")
    private String location;
    private RestType restType;
    @NotNull
    private int service;

    public RestaurantRequest(String name, String location, RestType restType, int service) {
        this.name = name;
        this.location = location;
        this.restType = restType;
        this.service = service;
    }

    public RestaurantRequest() {
    }
}
