package com.logistimo.locations.config.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Created by kumargaurav on 20/06/17.
 */
public class StandaloneCondition implements Condition {

  @Override
  public boolean matches(ConditionContext context,
                         AnnotatedTypeMetadata annotatedTypeMetadata) {
    return context.getEnvironment().getProperty("app.issentinel").contains("false");
  }
}
