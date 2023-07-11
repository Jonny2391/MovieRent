package it.cgmconsulting.addone.service;

import it.cgmconsulting.addone.entity.Film;
import it.cgmconsulting.addone.entity.Genre;
import it.cgmconsulting.addone.entity.Language;
import it.cgmconsulting.addone.payload.request.FilmRequest;
import it.cgmconsulting.addone.repository.FilmRepository;
import it.cgmconsulting.addone.repository.GenreRepository;
import it.cgmconsulting.addone.repository.LanguageRepository;
import it.cgmconsulting.addone.utils.Utils;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FilmService {

    final private FilmRepository filmRepository;
    final private LanguageRepository languageRepository;
    final private GenreRepository genreRepository;

    public ResponseEntity<String> updateWithRequest(long filmId, FilmRequest request) {

        String msg = "";
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        // Sistemo TITLE, GENRE e LANGUAGE in modo che rispettino il formato dei valori presenti nel database:
        // TITLE -> UPPERCASE
        // GENRE e LANGUAGE -> Solo la prima lettera maiuscola
        request.setTitle(request.getTitle().toUpperCase());
        request.setGenreName(request.getGenreName().substring(0,1).toUpperCase()+
                request.getGenreName().substring(1).toLowerCase());
        request.setLanguageName(request.getLanguageName().substring(0,1).toUpperCase()+
                request.getLanguageName().substring(1).toLowerCase());

        // Controlli
        if (!filmRepository.existsById(filmId)) {
            // 1) Esiste un film con il filmId passato?
            msg = "Film not found with film_id " + filmId;
            //return new ResponseEntity<>("Film not found", HttpStatus.NOT_FOUND);
        } else if (filmRepository.existsByTitleAndFilmIdNot(request.getTitle(), filmId)) {
            // 2) Esiste già un altro film con il titolo che stiamo passando?
            msg = "Title " + request.getTitle() + " already exists: can't exists two films with same title";
            httpStatus = HttpStatus.BAD_REQUEST;
        }

        // 3) Esistono Genre e Language passati?
        // Provo a estrarre LANGUAGE e GENRE
        Optional<Language> opLanguage = languageRepository.findByLanguageName(request.getLanguageName());
        Optional<Genre> opGenre = genreRepository.findByGenreName(request.getGenreName());

        // Controllo esistenza di LANGUAGE e GENRE
        msg += msg.equals("")?"":"\n";
        msg = msg + Utils.notFoundString("not found",
                Pair.of("Language", opLanguage.isPresent()),
                Pair.of("Genre", opGenre.isPresent()));

        // Se LANGUAGE e/o GENRE non esistono
        if (!msg.equals("")) {
            return new ResponseEntity<>(msg, httpStatus);
        }

        //aggiorno il FILM in database
        return this.saveFilmFromRequest(filmId, request, opGenre.get(), opLanguage.get());
    }

    // Salvo o aggiorno un Film a partire da una request (per un nuovo salvataggio filmId è 0)
    // AL MOMENTO NON C'È NESSUNA CHIAMATA A QUESTA FUNCTION PER LA CREAZIONE DI UN NUOVO FILM
    private ResponseEntity<String> saveFilmFromRequest(long filmId, FilmRequest request, Genre genre, Language language) {
        Film film = new Film(filmId, request.getTitle(), request.getDescription(),
                request.getReleaseYear(), language, genre);

        Film filmSaved = filmRepository.save(film);

        String msg = filmId == 0 ? "New film saved with film_id " + filmSaved.getFilmId() : "Film updated";

        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    public ResponseEntity<?> getByLanguage(long languageId) {
        if (!languageRepository.existsById(languageId)) {
            return new ResponseEntity<>("language_id " + languageId + " not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(filmRepository.getByLanguage(languageId), HttpStatus.OK);
    }
}
