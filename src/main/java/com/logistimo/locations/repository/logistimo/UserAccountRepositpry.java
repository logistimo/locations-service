package com.logistimo.locations.repository.logistimo;

import com.logistimo.locations.entity.logistimo.UserAccount;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by kumargaurav on 14/02/17.
 */
public interface UserAccountRepositpry extends JpaRepository<UserAccount, Long> {

  @Query(value = "SELECT k FROM UserAccount k")
  Page<UserAccount> findAllUser(Pageable pageable);

  @Query(value = "SELECT count(*) FROM  USERACCOUNT  WHERE ( DISTRICT IS NOT NULL AND DISTRICT <> '' AND DISTRICT_ID IS NULL ) "
      + "OR ( state IS NOT NULL AND STATE <> '' AND STATE_ID IS NULL AND ( DISTRICT IS NULL OR DISTRICT = ''))",
      nativeQuery = true)
  Integer countUserWithLOcIdsNull ();

  @Query(value = "SELECT * FROM  USERACCOUNT  WHERE ( DISTRICT IS NOT NULL AND DISTRICT <> '' AND DISTRICT_ID IS NULL ) "
      + "OR ( state IS NOT NULL AND STATE <> '' AND STATE_ID IS NULL AND ( DISTRICT IS NULL OR DISTRICT = '')) ORDER BY ?#{#pageable}",
      nativeQuery = true)
  Page<UserAccount> findUserWithLocIdsNull(Pageable pageable);
}
