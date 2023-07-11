package it.cgmconsulting.addone.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FilmMaxRentResponse {
    private long filmId;
    private String title;
    private long numberOfRents;

    public FilmMaxRentResponse(long filmId, String title) {
        this.filmId = filmId;
        this.title = title;
    }
}
