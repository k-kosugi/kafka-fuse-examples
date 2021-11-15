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
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        restConfiguration()
                .component("servlet")
                .port(50000)
                .bindingMode(RestBindingMode.json);

        // kafka
        from("timer?period=1s")
                .setBody(simple("Hello World From Timer"))
                .to("direct:toKafka");

        from("rest:get:say:/hello/{id}")
                .setBody(simple("${header.id}"))
                        .to("direct:toKafka");

        from("direct:toKafka")
                .to("kafka:{{camel.component.kafka.configuration.topic}}?brokers={{camel.component.kafka.brokers}}");
    }

}
