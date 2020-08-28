package com.kroger.scrs.exampleservice.adapter.out.ospclient;

import com.kroger.scrs.exampleservice.service.ThingPort;
import com.kroger.scrs.exampleservice.service.model.Thing;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
class OspClient implements ThingPort {

  private final OspClientProperties ospClientProperties;

  @Override
  public Optional<Thing> get(final String thingId) {
    // TODO: Implement
    return Optional.empty();
  }
}
