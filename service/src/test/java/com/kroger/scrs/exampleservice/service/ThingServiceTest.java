package com.kroger.scrs.exampleservice.service;

import static com.kroger.scrs.exampleservice.service.ThingService.SendThingEventCommand.INITIAL_ATTEMPT;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

import com.kroger.scrs.exampleservice.service.ThingService.SendThingEventCommand;
import com.kroger.scrs.exampleservice.service.model.AvroThing;
import com.kroger.scrs.exampleservice.service.model.Thing;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.val;
import org.junit.jupiter.api.Test;

class ThingServiceTest {

  private final ThingPort thingPort = mock(ThingPort.class);
  private final DomainEventDispatcherPort eventDispatcher = mock(DomainEventDispatcherPort.class);
  private ThingService sut= new ThingService(thingPort, new ThingMapper(), eventDispatcher);


  @Test
  void givenThingExists_thenDomainEventDispatched() {

    // given
    val actualThingId = "1234";
    val actualCorrelationId = "correlation-123";
    val actualThing = new Thing(actualThingId, 10);

    given(thingPort.get(eq(actualThingId)))
        .willReturn(Optional.of(actualThing));

    // when
    val command = new SendThingEventCommand(
        actualCorrelationId,
        actualThingId,
        INITIAL_ATTEMPT,
        LocalDateTime.now());

    sut.sendThing(command);

    // then
    then(eventDispatcher).should()
        .dispatch(new AvroThing(actualThingId, actualThing.getCount()));
  }

}
