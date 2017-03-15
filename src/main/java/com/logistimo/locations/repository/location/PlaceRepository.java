package com.logistimo.locations.repository.location;

import com.logistimo.locations.entity.location.Place;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by kumargaurav on 27/02/17.
 */
public interface PlaceRepository extends JpaRepository<Place,Long> {

  @Query(value = "SELECT * FROM PLACE WHERE PLACENAME=?1", nativeQuery = true)
  Place findByPlaceName(String place);

}
