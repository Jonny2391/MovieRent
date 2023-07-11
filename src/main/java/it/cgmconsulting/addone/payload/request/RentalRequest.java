package it.cgmconsulting.addone.payload.request;

import lombok.Getter;

@Getter
public class RentalRequest {
    private long customerId;
    private long storeId;
    private long inventoryId;
}
