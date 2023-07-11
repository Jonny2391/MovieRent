package it.cgmconsulting.addone.repository;

import it.cgmconsulting.addone.entity.FilmStaff;
import it.cgmconsulting.addone.entity.FilmStaffId;
import it.cgmconsulting.addone.payload.response.FilmResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.RequestEntity;

import java.util.List;
import java.util.Set;

public interface FilmStaffRepository extends JpaRepository<FilmStaff, FilmStaffId> {

    @Query(value = "SELECT new it.cgmconsulting.addone.payload.response.FilmResponse(" +
            "fs.id.film.filmId, " +
            "fs.id.film.title, " +
            "fs.id.film.description, " +
            "fs.id.film.releaseYear, " +
            "fs.id.film.language.languageName) " +
            "FROM FilmStaff fs " +
            "WHERE fs.id.staff.staffId IN :actorIds " +
            "GROUP BY fs.id.film.filmId " +
            "HAVING COUNT(fs.id.film.filmId) = :nActors " +
            "ORDER BY fs.id.film.title")
    List<FilmResponse> getFilmsByActors(@Param(value = "actorIds") Set<Long> actorIds,@Param(value = "nActors") int nActors);
}
