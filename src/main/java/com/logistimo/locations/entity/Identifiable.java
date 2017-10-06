package com.logistimo.locations.entity;

import java.io.Serializable;

/**
 * Created by kumargaurav on 03/10/17.
 */
public interface Identifiable<T extends Serializable> {

  T getEntityName();
}
