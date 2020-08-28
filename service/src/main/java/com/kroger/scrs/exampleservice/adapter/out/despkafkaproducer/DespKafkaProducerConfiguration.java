package com.kroger.scrs.exampleservice.adapter.out.despkafkaproducer;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@Configuration
@ComponentScan
@EnableKafka
@EnableConfigurationProperties(DespKafkaProducerProperties.class)
@ConditionalOnProperty(
    name = "desp-kafka-producer.enabled",
    havingValue = "true",
    matchIfMissing = true)
public class DespKafkaProducerConfiguration {

}
