package com.logistimo.locations.repository.location;

import com.logistimo.locations.entity.location.Country;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by kumargaurav on 08/02/17.
 */
public interface CountryRepository extends JpaRepository<Country, Long> {

  @Query(value = "SELECT * FROM COUNTRY WHERE COUNTRYNAME = ?1",nativeQuery = true)
  Country findByName(String country);


  @Query(value = "SELECT * FROM COUNTRY WHERE COUNTRYCODE = ?1",nativeQuery = true)
  Country findByCode(String country);

}

