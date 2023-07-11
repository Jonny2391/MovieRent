package it.cgmconsulting.addone.repository;

import it.cgmconsulting.addone.entity.Inventory;
import it.cgmconsulting.addone.payload.response.FilmRentableResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    @Query(value = "SELECT new it.cgmconsulting.addone.payload.response.FilmRentableResponse(" +
            "i.film.title, " +
            "i.store.storeName, " +
            "COUNT(i.film.title)) " +
            "FROM Inventory i " +
            "WHERE i.inventoryId IN :inventoryIds " +
            "GROUP BY i.store.storeName")
    Set<FilmRentableResponse> getRentableFilms(@Param("inventoryIds") Set<Long> inventoryIds);

    @Query(value = "SELECT i.inventoryId " +
            "FROM Inventory i " +
            "WHERE i.film.title = :title")
    TreeSet<Long> getIdByTitle(@Param("title") String title);

    boolean existsByInventoryIdAndStoreStoreId(long inventoryId, long storeId);
}
