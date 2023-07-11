package it.cgmconsulting.addone.repository;

import it.cgmconsulting.addone.entity.Film;
import it.cgmconsulting.addone.payload.response.FilmMaxRentResponse;
import it.cgmconsulting.addone.payload.response.FilmResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FilmRepository extends JpaRepository<Film, Long> {

    @Query(value = "SELECT new it.cgmconsulting.addone.payload.response.FilmResponse(" +
    "f.filmId, " +
    "f.title, " +
    "f.description, " +
    "f.releaseYear, " +
    "f.language.languageName) " +
    "FROM Film f " +
    "WHERE f.language.languageId = :languageId")
    List<FilmResponse> getByLanguage(@Param(value = "languageId") long languageId);

    boolean existsByTitleAndFilmIdNot(String title, long filmId);
}
