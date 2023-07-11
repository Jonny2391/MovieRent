package it.cgmconsulting.addone.controller;

import it.cgmconsulting.addone.entity.Inventory;
import it.cgmconsulting.addone.repository.InventoryRepository;
import it.cgmconsulting.addone.service.InventoryService;
import it.cgmconsulting.addone.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class InventoryController {

    final private InventoryService inventoryService;
    final private InventoryRepository inventoryRepository;

    @PutMapping("/add-film-to-store/{storeId}/{filmId}")
    public ResponseEntity<?> addFilm(@PathVariable(name = "storeId") long storeId, @PathVariable(name = "filmId") long filmId){
        return inventoryService.addFilm(storeId, filmId);
    }

    @GetMapping("/find-rentable-films")
    public ResponseEntity<?> getRentableFilms(@RequestParam String title){
        return inventoryService.getRentableFilms(title.toUpperCase());
    }
}
