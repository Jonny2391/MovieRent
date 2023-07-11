package it.cgmconsulting.addone.service;

import it.cgmconsulting.addone.entity.Customer;
import it.cgmconsulting.addone.entity.Inventory;
import it.cgmconsulting.addone.entity.Rental;
import it.cgmconsulting.addone.entity.RentalId;
import it.cgmconsulting.addone.payload.request.RentalRequest;
import it.cgmconsulting.addone.payload.response.CustomerStoreResponse;
import it.cgmconsulting.addone.payload.response.FilmMaxRentResponse;
import it.cgmconsulting.addone.repository.*;
import it.cgmconsulting.addone.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepository;
    private final StoreRepository storeRepository;
    private final CustomerRepository customerRepository;
    private final InventoryRepository inventoryRepository;

    public ResponseEntity<?> getNumberPerStore(String storeName) {
        if (!storeRepository.existsByStoreName(storeName))
            return new ResponseEntity<>("Store " + storeName + " not found", HttpStatus.NOT_FOUND);

        CustomerStoreResponse customerStoreResponse = rentalRepository.getNumberPerStore(storeName);

        // Nel caso lo store non abbia ancora effettuato prestiti è necessario valorizzare
        // lo storeName
        customerStoreResponse.setStoreName(storeName);

        return new ResponseEntity<>(customerStoreResponse, HttpStatus.OK);
    }

    public ResponseEntity<?> addUpRental(RentalRequest request) {
        String msg = "";
        boolean existStore = storeRepository.existsById(request.getStoreId());
        boolean existInventory = inventoryRepository.existsById(request.getInventoryId());

        if (existStore && existInventory
                && !inventoryRepository.existsByInventoryIdAndStoreStoreId(request.getInventoryId(),request.getStoreId())){
            msg = "The article " + request.getInventoryId() + " doen't belong to the store " + request.getStoreId();
        }

        // Controllo l'esistenza del CUSTOMER, dello STORE e dell'INVENTORY (oggetto di inventario) nel database
        msg += msg.equals("")?"":"\n";
        msg += Utils.notFoundString("not found",
                Pair.of("Customer", customerRepository.existsById(request.getCustomerId())),
                Pair.of("Store", existStore),
                Pair.of("Inventory object", existInventory)
        );

        if (!msg.equals("")) {
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }

        Optional<Rental> opRentals = rentalRepository
                .findByRentalIdCustomerCustomerIdAndRentalIdInventoryInventoryIdAndRentalReturnIsNull(request.getCustomerId(), request.getInventoryId());

        Rental rental;

        //In caso di restituzione
        if (opRentals.isPresent()) {
            rental = opRentals.get();
            rental.setRentalReturn(LocalDateTime.now());
            msg = "Film returned";
        } else {
            Customer c = new Customer(request.getCustomerId());
            Inventory i = new Inventory(request.getInventoryId());
            rental = new Rental(new RentalId(c, i, LocalDateTime.now()));
            msg = "Film rented";
        }

        rentalRepository.save(rental);

        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    public ResponseEntity<?> countInPeriod(long storeId, LocalDateTime start, LocalDateTime end) {

        if(!storeRepository.existsById(storeId)){
            return new ResponseEntity<>("Store " + storeId + " doesn't exists!", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(rentalRepository.countInPeriod(storeId, start, end), HttpStatus.OK);
    }

    public ResponseEntity<?> getFilmsByCustomer(long customerId) {
        if (!customerRepository.existsById(customerId))
            return new ResponseEntity<>("Customer " + customerId + " not found", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(rentalRepository.getFilmsByCustomer(customerId), HttpStatus.OK);
    }

    public ResponseEntity<?> getFilmsMaxNumRent() {
        List<FilmMaxRentResponse> films = rentalRepository.getAllFilms();

        return new ResponseEntity<>(films.stream().filter(f -> f.getNumberOfRents() == films.get(0).getNumberOfRents()), HttpStatus.OK);
    }
}
