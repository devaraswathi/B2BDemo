package com.kroger.scrs.exampleservice.service;

import com.kroger.scrs.exampleservice.service.model.AvroThing;
import com.kroger.scrs.exampleservice.service.model.Thing;
import org.springframework.stereotype.Component;

@Component
class ThingMapper {

  public AvroThing toThingAvro(final Thing source) {
    return new AvroThing(source.getId(), source.getCount());
  }
}
