package com.redhat.japan;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaComponent;
import org.apache.camel.component.kafka.KafkaConfiguration;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.component.kafka.KafkaManualCommit;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // kafka
        from("kafka:{{camel.component.kafka.configuration.topic}}?brokers={{camel.component.kafka.brokers}}")
                .log("commit() succeeded.")
                .log("${body}");
    }

}
