package com.kroger.scrs.exampleservice.common;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class CorrelationService {

  public String nextCorrelationId() {
    return UUID.randomUUID().toString();
  }
}
