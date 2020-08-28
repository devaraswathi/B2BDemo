package com.kroger.scrs.exampleservice.adapter.out.despkafkaproducer;

import javax.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties("desp-kafka-producer")
@ConstructorBinding
@Data
public class DespKafkaProducerProperties {

  @NotEmpty(message = "desp-kafka-producer.broker-address is required.")
  final String brokerAddress;
}
