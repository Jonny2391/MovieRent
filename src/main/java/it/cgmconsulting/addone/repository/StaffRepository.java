package it.cgmconsulting.addone.repository;

import it.cgmconsulting.addone.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface StaffRepository extends JpaRepository<Staff,Long> {
    @Query(value = "SELECT s.staffId " +
            "FROM Staff s " +
            "WHERE s.staffId in :actorIds")
    Set<Long> getAllByIds(@Param(value = "actorIds") Set<Long> actorIds);
}
