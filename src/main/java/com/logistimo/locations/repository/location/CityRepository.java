package com.logistimo.locations.repository.location;

import com.logistimo.locations.entity.location.City;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by kumargaurav on 27/02/17.
 */
public interface CityRepository extends JpaRepository<City,Long> {

  @Query(value = "SELECT * FROM CITY WHERE PLACENAME=?1", nativeQuery = true)
  City findByPlaceName(String place);

}
