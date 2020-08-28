package com.kroger.scrs.exampleservice.service;

import com.kroger.scrs.exampleservice.service.model.Thing;
import java.util.Optional;

public interface ThingPort {

  Optional<Thing> get(final String thingId);
}
