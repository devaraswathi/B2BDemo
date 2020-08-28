package com.kroger.scrs.exampleservice.service;

public class ThingNotFoundException extends RuntimeException {

  public ThingNotFoundException() {
    super("Thing not found.");
  }
}
