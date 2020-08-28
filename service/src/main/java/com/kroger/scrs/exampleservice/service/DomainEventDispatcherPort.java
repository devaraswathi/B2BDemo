package com.kroger.scrs.exampleservice.service;

public interface DomainEventDispatcherPort {

  <T> void dispatch(T payload);
}
