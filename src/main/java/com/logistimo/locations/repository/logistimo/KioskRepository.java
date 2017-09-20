package com.logistimo.locations.repository.logistimo;

import com.logistimo.locations.entity.logistimo.Kiosk;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by kumargaurav on 14/02/17.
 */
public interface KioskRepository extends JpaRepository<Kiosk,Long> {

  @Query(value = "SELECT k FROM Kiosk k")
  Page<Kiosk> findAllKiosk(Pageable pageable);

  @Query(value = "SELECT count(*) FROM  KIOSK  WHERE ( DISTRICT IS NOT NULL AND DISTRICT <> '' AND DISTRICT_ID IS NULL ) "
      + "OR ( state IS NOT NULL AND STATE <> '' AND STATE_ID IS NULL AND ( DISTRICT IS NULL OR DISTRICT = ''))",
      nativeQuery = true)
  Integer countKioskWithLOcIdsNull ();

  @Query(value = "SELECT * FROM  KIOSK  WHERE ( DISTRICT IS NOT NULL AND DISTRICT <> '' AND DISTRICT_ID IS NULL ) "
      + "OR ( state IS NOT NULL AND STATE <> '' AND STATE_ID IS NULL AND ( DISTRICT IS NULL OR DISTRICT = ''))",
      nativeQuery = true)
  Page<Kiosk> findKioskWithLocIdsNull(Pageable pageable);

}
