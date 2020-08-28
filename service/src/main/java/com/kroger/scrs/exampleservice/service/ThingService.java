package com.kroger.scrs.exampleservice.service;

import com.kroger.scrs.exampleservice.common.SelfValidating;
import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ThingService {

  private final ThingPort thingPort;
  private final ThingMapper mapper;
  private final DomainEventDispatcherPort eventDispatcher;

  // Define a command parameter to be used to invoke the use case.
  @Getter
  @EqualsAndHashCode(callSuper = false)
  public static class SendThingEventCommand extends SelfValidating<SendThingEventCommand> {

    public static int INITIAL_ATTEMPT = 0;

    @NotEmpty(message = "correlationId is required.")
    String correlationId;

    @NotEmpty(message = "thingId is required.")
    String thingId;

    @PositiveOrZero(message = "attempt must be zero or greater.")
    int attempt = INITIAL_ATTEMPT;

    @PastOrPresent(message = "created cannot be in the future.")
    LocalDateTime created;

    // Ctors

    public SendThingEventCommand(
        String correlationId,
        String thingId,
        int attempt,
        LocalDateTime created) {

      this.correlationId = correlationId;
      this.attempt = attempt;
      this.created = created;
      this.thingId = thingId;

      // Call the super class to perform field validation and throw if invalid.
      validateSelf();
    }
  }

  public void sendThing(final SendThingEventCommand command) {

    thingPort
        .get(command.thingId)
        .map(mapper::toThingAvro)
        .ifPresentOrElse(

            // If present...
            eventDispatcher::dispatch,

            // or else...
            ThingNotFoundException::new);
  }
}

