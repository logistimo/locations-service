package com.logistimo.locations.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;

/**
 * Created by kumargaurav on 06/03/17.
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LocationValidator.class)
public @interface ValidLocation {

  String message() default "{Location request model not valid! }";

  Class[] groups() default {};

  Class[] payload() default {};
}
