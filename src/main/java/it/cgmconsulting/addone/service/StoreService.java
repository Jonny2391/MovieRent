package it.cgmconsulting.addone.service;

import it.cgmconsulting.addone.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public boolean existsByStoreName(String storeName) {
        return storeRepository.existsByStoreName(storeName);
    }
}
