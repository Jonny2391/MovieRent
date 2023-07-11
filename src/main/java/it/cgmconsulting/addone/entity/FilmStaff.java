package it.cgmconsulting.addone.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class FilmStaff {
    @EqualsAndHashCode.Include
    @EmbeddedId
    private FilmStaffId id;
}
