package com.kroger.scrs.exampleservice.adapter.in.ospwebhook;

import static com.kroger.scrs.exampleservice.service.ThingService.SendThingEventCommand.INITIAL_ATTEMPT;

import com.kroger.scrs.exampleservice.adapter.in.ospwebhook.validators.ValidWebhookEvent;
import com.kroger.scrs.exampleservice.adapter.in.ospwebhook.validators.ValidWebhookSignature;
import com.kroger.scrs.exampleservice.common.CorrelationService;
import com.kroger.scrs.exampleservice.common.TimeService;
import com.kroger.scrs.exampleservice.service.ThingService;
import com.kroger.scrs.exampleservice.service.ThingService.SendThingEventCommand;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.val;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping(OspWebhookController.BASE_RESOURCE_URL)
class OspWebhookController {

  static final String BASE_RESOURCE_URL = "/v1/osp-webhook/things";

  final ThingService thingService;
  final TimeService timeService;
  final CorrelationService correlationService;

  // There may be two options possible when configuring webhooks.
  //
  // 1) A single URI is re-used for all webhooks from the same domain. For instance,
  //    purchase order. In a shared URI scenario, the individual events can be routed
  //    to controller methods based on header value(s).
  //
  // 2) Each webhook event is configured with a unique URI. In that scenario, header
  //    validation is necessary to enforce that the webhook URI and expected headers match.
  //
  // It may be simpler to manage option #1 and eliminate the need to valid headers for
  // unique URIs.
  //
  @PostMapping(headers = {"X-OSP-WEBHOOK-EVENT-NAME=thingAction", "X-OSP-WEBHOOK-EVENT-VERSION=2"})
  void thingAction(

      @ValidWebhookSignature()
      // Next validation line is not needed if header routing is used.
      @ValidWebhookEvent(eventName = "thingAction", eventVersion = "2")
      final HttpServletRequest request,

      @Valid @RequestBody final WebhookPayload payload
  ) {

    val command = new SendThingEventCommand(
        correlationService.nextCorrelationId(),
        payload.getThingId(),
        INITIAL_ATTEMPT,
        timeService.now());

    thingService.sendThing(command);

  }

  /**
   * Internal request payload class needed to marshall data. Data will be extracted
   * and used to define a command object for the service.
   */
  @Data
  private static class WebhookPayload {
    @NotEmpty(message = "thingId is required.")
    String thingId;
  }
}
