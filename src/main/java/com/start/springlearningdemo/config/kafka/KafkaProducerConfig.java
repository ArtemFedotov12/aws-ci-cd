package com.start.springlearningdemo.config.kafka;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

import static com.start.springlearningdemo.constants.ApplicationConstants.*;

@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.security.protocol}")
    private String securityProtocol;

    @Value("${kafka.sasl.jaas.config}")
    private String jaasConfig;

    @Value("${kafka.sasl.mechanism}")
    private String saslMechanism;


    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.ByteArraySerializer.class);

        props.put(KAFKA_SECURITY_PROTOCOL_PROPERTY, securityProtocol);
        props.put(KAFKA_SASL_JAAS_CONFIG_PROPERTY, jaasConfig);
        props.put(KAFKA_SASL_MECHANISM_PROPERTY, saslMechanism);
        return props;
    }

    @Bean
    public <K, V> ProducerFactory<K, V> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean("createKafkaTemplate")
    public <K, V> KafkaTemplate<K, V> createKafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }

}
