package it.cgmconsulting.addone.service;

import it.cgmconsulting.addone.common.FilmStoreAway;
import it.cgmconsulting.addone.entity.Film;
import it.cgmconsulting.addone.entity.Inventory;
import it.cgmconsulting.addone.entity.Store;
import it.cgmconsulting.addone.payload.response.FilmRentableResponse;
import it.cgmconsulting.addone.repository.FilmRepository;
import it.cgmconsulting.addone.repository.InventoryRepository;
import it.cgmconsulting.addone.repository.RentalRepository;
import it.cgmconsulting.addone.repository.StoreRepository;
import it.cgmconsulting.addone.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryService {
    final private StoreRepository storeRepository;
    final private FilmRepository filmRepository;
    final private InventoryRepository inventoryRepository;
    final private RentalRepository rentalRepository;

    public ResponseEntity<?> addFilm(long storeId, long filmId) {

        // Controllo l'esistenza dello STORE e del FILM nel database
        String msg = Utils.notFoundString("not found",
                Pair.of("Film", filmRepository.existsById(filmId)),
                Pair.of("Store", storeRepository.existsById(storeId))
        );

        if (!msg.equals("")) {
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }

        inventoryRepository.save(new Inventory(new Store(storeId), new Film(filmId)));

        return new ResponseEntity<>("Film added to store", HttpStatus.OK);
    }

    public ResponseEntity<?> getRentableFilms(String title) {
        // inventoryId di tutte le copie col dato titolo (per velocizzare select da Rental)
        TreeSet<Long> inventoryIds = inventoryRepository.getIdByTitle(title);

        if(inventoryIds.isEmpty()){
            return new ResponseEntity<>("No film with title " + title + " in inventory",HttpStatus.NOT_FOUND);
        }

        //Copie in prestito
        Set<FilmStoreAway> filmsAwayFromStore = rentalRepository.getFilmsAwayFromStore(inventoryIds);

        // inventoryId delle copie in prestito
        Set<Long> inventoryIdsAwayFromStore = filmsAwayFromStore.stream().map(o -> o.getInventoryId()).collect(Collectors.toSet());

        // In inventoryIds tengo solo le copie disponibili
        inventoryIdsAwayFromStore.forEach(inventoryIds::remove);

        // Recupero i dati di tutti le copie disponibili
        Set<FilmRentableResponse> rentableFilms = inventoryRepository.getRentableFilms(inventoryIds);

        // Aggiorno il campo total in rentableFilms
        // Ad ogni iterazione calcolo il totale di copie di un negozio
        for(FilmRentableResponse rentable: rentableFilms){
            long total = filmsAwayFromStore.size();

            // Tolgo da filmsAwayFromStore gli elementi conteggiati in questa iterazione
            filmsAwayFromStore.stream().filter(away -> away.getStoreName() != rentable.getStoreName());

            // Il totale è pari al numero di elementi tolti prima
            rentable.setTotal(rentable.getAvailable() + (total - filmsAwayFromStore.size()));
        }

        return new ResponseEntity<>(rentableFilms,HttpStatus.OK);
    }
}
