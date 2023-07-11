package it.cgmconsulting.addone.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Rental {
    @EqualsAndHashCode.Include
    @EmbeddedId
    private RentalId rentalId;

    private LocalDateTime rentalReturn;

    public Rental(RentalId id) {
        this.rentalId = id;
    }
}
