package com.kroger.scrs.exampleservice;

import com.kroger.scrs.exampleservice.adapter.in.ospwebhook.OspWebhookConfiguration;
import com.kroger.scrs.exampleservice.adapter.in.webbootstrap.WebBootstrapConfiguration;
import com.kroger.scrs.exampleservice.adapter.out.despkafkaproducer.DespKafkaProducerConfiguration;
import com.kroger.scrs.exampleservice.adapter.out.ospclient.OspClientConfiguration;
import com.kroger.scrs.exampleservice.common.CorrelationService;
import com.kroger.scrs.exampleservice.common.ObjectMapperConfiguration;
import com.kroger.scrs.exampleservice.common.TimeService;
import com.kroger.scrs.exampleservice.service.ApplicationConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootConfiguration
@EnableAutoConfiguration
@Import({
    TimeService.class,
    CorrelationService.class,
    ObjectMapperConfiguration.class,
    WebBootstrapConfiguration.class,
    OspWebhookConfiguration.class,
    DespKafkaProducerConfiguration.class,
    OspClientConfiguration.class,
    ApplicationConfiguration.class})
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
