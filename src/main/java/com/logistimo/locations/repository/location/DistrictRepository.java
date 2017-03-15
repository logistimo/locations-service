package com.logistimo.locations.repository.location;

import com.logistimo.locations.entity.location.District;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by kumargaurav on 27/02/17.
 */
public interface DistrictRepository extends JpaRepository<District, Long> {

  @Query(value = "SELECT * FROM DISTRICT WHERE DISTNAME = ?1",nativeQuery = true)
  District findByName(String district);

}
