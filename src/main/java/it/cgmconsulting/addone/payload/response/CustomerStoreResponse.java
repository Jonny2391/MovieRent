package it.cgmconsulting.addone.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CustomerStoreResponse {
    public String storeName;
    public long totalCustomers;
}
