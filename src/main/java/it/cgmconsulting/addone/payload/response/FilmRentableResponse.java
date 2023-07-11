package it.cgmconsulting.addone.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FilmRentableResponse {
    private String title;
    private String storeName;
    private long total;
    private long available;

    public FilmRentableResponse(String title, String storeName, long available) {
        this.title = title;
        this.storeName = storeName;
        this.available = available;
    }
}
