package it.cgmconsulting.addone.repository;

import it.cgmconsulting.addone.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
    boolean existsByStoreName(String storeName);
}
