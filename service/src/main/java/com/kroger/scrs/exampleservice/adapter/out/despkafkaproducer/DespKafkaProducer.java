package com.kroger.scrs.exampleservice.adapter.out.despkafkaproducer;

import com.kroger.scrs.exampleservice.service.DomainEventDispatcherPort;
import org.springframework.stereotype.Component;

@Component
class DespKafkaProducer implements DomainEventDispatcherPort {

  @Override
  public <T> void dispatch(T event) {
    // TODO: implement
  }
}
