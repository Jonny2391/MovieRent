package it.cgmconsulting.addone.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FilmRentResponse {
    public long filmId;
    public String title;
    public String storeName;
}
