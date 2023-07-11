package it.cgmconsulting.addone.payload.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class FilmRequest {
    @NotBlank
    @Size(max = 100)
    private String title;
    @NotBlank
    private String description;
    @Min(1900) @Max(2100)
    private short releaseYear;
    @NotBlank @Size(max = 20)
    private String languageName;
    @NotBlank @Size(max = 20)
    private String genreName;
}
