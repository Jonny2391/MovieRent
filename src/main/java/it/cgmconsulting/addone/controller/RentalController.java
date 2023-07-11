package it.cgmconsulting.addone.controller;

import it.cgmconsulting.addone.payload.request.RentalRequest;
import it.cgmconsulting.addone.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class RentalController {

    final private RentalService rentalService;

    @PutMapping("/add-update-rental")
    public ResponseEntity<?> addUpRental(@RequestBody RentalRequest request){
        return rentalService.addUpRental(request);
    }

    @GetMapping("/count-customers-by-store/{storeName}")
    // CustomerStoreResponse
    public ResponseEntity<?> getNumberPerStore(@PathVariable(name = "storeName") String storeName){
        return rentalService.getNumberPerStore(storeName.toUpperCase());
    }

    @GetMapping("/count-rentals-in-date-range-by-store/{storeId}")
    public ResponseEntity<?> countInPeriod(@PathVariable(name = "storeId") long storeId, @RequestParam LocalDate start, @RequestParam LocalDate end){
        return rentalService.countInPeriod(storeId, LocalDateTime.of(start, LocalTime.MIN), LocalDateTime.of(end, LocalTime.MAX));
    }

    @GetMapping("/find-all-films-rent-by-one-customer/{customerId}")
    public ResponseEntity<?> getFilmsByCustomer(@PathVariable(name = "customerId") long customerId){
        return rentalService.getFilmsByCustomer(customerId);
    }

    @GetMapping("/find-film-with-max-number-of-rent")
    public ResponseEntity<?> getFilmsMaxNumRent(){
        return rentalService.getFilmsMaxNumRent();
    }
}
