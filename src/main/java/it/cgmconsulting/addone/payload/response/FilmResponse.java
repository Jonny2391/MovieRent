package it.cgmconsulting.addone.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FilmResponse {
    private long filmId;
    private String title;
    private String description;
    private short releaseYear;
    private String languageName;
}
