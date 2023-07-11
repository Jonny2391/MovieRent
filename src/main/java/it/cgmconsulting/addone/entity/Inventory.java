package it.cgmconsulting.addone.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class Inventory {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long inventoryId;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne
    @JoinColumn(name = "film_id", nullable = false)
    private Film film;

    @OneToMany(mappedBy = "rentalId.inventory", fetch = FetchType.EAGER)
    private Set<Rental> rentals;

    public Inventory(Store store, Film film) {
        this.store = store;
        this.film = film;
    }

    public Inventory(long inventoryId) {
        this.inventoryId = inventoryId;
    }
}
