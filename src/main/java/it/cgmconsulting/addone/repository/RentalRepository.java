package it.cgmconsulting.addone.repository;

import it.cgmconsulting.addone.common.FilmStoreAway;
import it.cgmconsulting.addone.entity.Rental;
import it.cgmconsulting.addone.payload.response.CustomerStoreResponse;
import it.cgmconsulting.addone.payload.response.FilmMaxRentResponse;
import it.cgmconsulting.addone.payload.response.FilmRentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    @Query(value = "SELECT new it.cgmconsulting.addone.payload.response.CustomerStoreResponse(" +
            "r.rentalId.inventory.store.storeName, " +
            "COUNT( DISTINCT r.rentalId.customer.customerId)) " +
            "FROM Rental r " +
            "WHERE r.rentalId.inventory.store.storeName = :storeName")
    CustomerStoreResponse getNumberPerStore(@Param(value = "storeName")String storeName);

    Optional<Rental> findByRentalIdCustomerCustomerIdAndRentalIdInventoryInventoryIdAndRentalReturnIsNull(long customerId, long inventoryId);

    @Query(value = "SELECT COUNT(*) FROM Rental r " +
    "WHERE r.rentalId.inventory.store.storeId = :storeId " +
    "AND r.rentalId.rentalDate BETWEEN :start AND :end")
    long countInPeriod(@Param(value = "storeId")long storeId,@Param(value = "start") LocalDateTime start,@Param(value = "end") LocalDateTime end);

    @Query(value = "SELECT new it.cgmconsulting.addone.payload.response.FilmRentResponse(" +
    "r.rentalId.inventory.film.filmId, " +
    "r.rentalId.inventory.film.title, " +
    "r.rentalId.inventory.store.storeName) " +
    "FROM Rental r " +
    "WHERE r.rentalId.customer.customerId = :customerId " +
            "GROUP BY r.rentalId.inventory.film.filmId " +
            "ORDER BY r.rentalId.inventory.film.filmId")
    List<FilmRentResponse> getFilmsByCustomer(@Param(value = "customerId") long customerId);

    @Query(value = "SELECT new it.cgmconsulting.addone.payload.response.FilmMaxRentResponse(" +
            "r.rentalId.inventory.film.filmId, " +
            "r.rentalId.inventory.film.title, " +
            "COUNT(r.rentalId.inventory.film.filmId) as c) " +
            "FROM Rental r " +
            "GROUP BY r.rentalId.inventory.film.filmId " +
            "ORDER BY c DESC"
    )
    List<FilmMaxRentResponse> getAllFilms();

    @Query(value = "SELECT new it.cgmconsulting.addone.common.FilmStoreAway(" +
            "r.rentalId.inventory.inventoryId, " +
            "r.rentalId.inventory.store.storeName)" +
            "FROM Rental r " +
            "WHERE r.rentalId.inventory.inventoryId IN :inventoryIds " +
            "AND r.rentalReturn IS NULL")
    Set <FilmStoreAway> getFilmsAwayFromStore(@Param("inventoryIds") Set<Long> inventoryIds);
}
