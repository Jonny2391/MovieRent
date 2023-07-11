package it.cgmconsulting.addone.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class Store {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long storeId;

    @Column(unique = true, length = 60)
    private String storeName;

    public Store(long storeId) {
        this.storeId = storeId;
    }
}
