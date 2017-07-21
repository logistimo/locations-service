package com.logistimo.locations.repository.location;

import com.logistimo.locations.entity.location.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by kumargaurav on 27/02/17.
 */
public interface StateRepository extends JpaRepository<State,Long> {

    @Query(value = "SELECT * FROM STATE WHERE COUNTRYID = ?1 AND STATENAME = ?2", nativeQuery = true)
    State findByName(String countryId, String state);
}
