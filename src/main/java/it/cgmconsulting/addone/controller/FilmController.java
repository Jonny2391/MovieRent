package it.cgmconsulting.addone.controller;

import it.cgmconsulting.addone.entity.Film;
import it.cgmconsulting.addone.payload.request.FilmRequest;
import it.cgmconsulting.addone.payload.response.FilmResponse;
import it.cgmconsulting.addone.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @PatchMapping("/update-film/{filmId}")
    public ResponseEntity<?> update(@RequestBody FilmRequest request, @PathVariable(name = "filmId") long filmId){
        return filmService.updateWithRequest(filmId, request);

    }

    @GetMapping("/find-films-by-language/{languageId}")
    //List<FilmResponse>
    public ResponseEntity<?> getByLanguage(@PathVariable(name = "languageId") long languageId){
        return filmService.getByLanguage(languageId);
    }
}
