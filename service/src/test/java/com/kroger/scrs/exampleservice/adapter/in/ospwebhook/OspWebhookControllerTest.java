package com.kroger.scrs.exampleservice.adapter.in.ospwebhook;

import static com.kroger.scrs.exampleservice.service.ThingService.SendThingEventCommand.INITIAL_ATTEMPT;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kroger.scrs.exampleservice.adapter.in.webbootstrap.WebBootstrapConfiguration;
import com.kroger.scrs.exampleservice.common.CorrelationService;
import com.kroger.scrs.exampleservice.service.ThingService;
import com.kroger.scrs.exampleservice.service.ThingService.SendThingEventCommand;
import com.kroger.scrs.exampleservice.common.ObjectMapperConfiguration;
import com.kroger.scrs.exampleservice.common.TimeService;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = OspWebhookController.class)
@Import({ObjectMapperConfiguration.class, WebBootstrapConfiguration.class})
class OspWebhookControllerIT {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private ThingService thingService;

  @MockBean
  private TimeService timeService;

  @MockBean
  private CorrelationService correlationService;

  @MockBean
  private HealthEndpoint healthEndpoint;

  @Test
  void sendThingEventFired() throws Exception {

    // given
    val actualThingId = "1234";
    val actualCorrelationId = "correlation-value";
    val actualJsonPayload = objectMapper.writeValueAsString(Map.of("thingId", actualThingId));

    // when
    when(timeService.now()).thenReturn(LocalDateTime.now());
    when(correlationService.nextCorrelationId()).thenReturn(actualCorrelationId);

    mockMvc.perform(post(OspWebhookController.BASE_RESOURCE_URL)
        .content(actualJsonPayload)
        .header("Content-Type", "application/json")
        .header("X-OSP-WEBHOOK-SIGNATURE", "H9FcRW0isRRMPgHZaa/+Vb4ueSnAKiKoxYtiaPVgQoKAw==")
        .header("X-OSP-WEBHOOK-SIGNATURE-KEY", "9f4d72ee-18f2-469d-8a5e-a6b9fa8aa2cc")
        .header("X-OSP-WEBHOOK-EVENT-NAME", "thingAction")
        .header("X-OSP-WEBHOOK-EVENT-VERSION", "2"))
        .andExpect(status().isOk());

    // then
    then(thingService).should()
        .sendThing(eq(new SendThingEventCommand(
            actualCorrelationId,
            actualThingId,
            INITIAL_ATTEMPT,
            timeService.now())));
  }
}
