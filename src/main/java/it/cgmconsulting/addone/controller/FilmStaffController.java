package it.cgmconsulting.addone.controller;

import it.cgmconsulting.addone.repository.FilmStaffRepository;
import it.cgmconsulting.addone.service.FilmStaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class FilmStaffController {

    final private FilmStaffService filmStaffService;

    @GetMapping("/find-films-by-actors")
    public ResponseEntity<?> getFilmByAllActors(@RequestParam Set<Long> actorIds){
        return filmStaffService.getFilmsByActors(actorIds);
    }
}
