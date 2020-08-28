package com.kroger.scrs.exampleservice.adapter.in.webbootstrap;

import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.boot.actuate.health.ApplicationHealthIndicator;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.health.Status;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping(BootstrapController.BASE_RESOURCE_URL)
public class BootstrapController {

  // TODO: Change 'resources' to the plural form of the resource served
  static final String BASE_RESOURCE_URL = "/actuator";

  private final HealthEndpoint healthEndpoint;

  @GetMapping("/liveness")
  // TODO: Find replacement for deprecated ApplicationHealthIndicator class.
  public Status liveness() {
    return new ApplicationHealthIndicator().health().getStatus();
  }

  @GetMapping("/readiness")
  public ResponseEntity<?> readiness() {
    val health = healthEndpoint.health();

    return health.getStatus().equals(Status.UP)
        ? ResponseEntity.ok(health)
        : ResponseEntity.status(SERVICE_UNAVAILABLE).body(health);
  }
}
