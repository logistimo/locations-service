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

    @Query(value = "SELECT * FROM CITY WHERE COUNTRYID = ?1 AND STATEID = ?2 AND PLACENAME=?3", nativeQuery = true)
    City findByCountryStatePlaceName(String countryId, String stateId, String place);

    @Query(value = "SELECT * FROM CITY WHERE COUNTRYID = ?1 AND STATEID = ?2 AND DISTID = ?3 AND PLACENAME=?4", nativeQuery = true)
    City findByCountryStateDistPlaceName(String countryId, String stateId, String distId, String place);

    @Query(value = "SELECT * FROM CITY WHERE COUNTRYID = ?1 AND STATEID = ?2 AND DISTID = ?3  AND SUBDISTID = ?4  AND PLACENAME=?5", nativeQuery = true)
    City findByCountryStateDistTalukPlaceName(String countryId, String stateId, String distId, String subdistId, String place);

}
