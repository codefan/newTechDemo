package com.centit.demo.kafka;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.common.config.SaslConfigs;

import java.util.Properties;

public class KafkaConfig {


    private String topic;

    private String consumerGroup;

    public KafkaConfig(){
        topic = "test";
        consumerGroup = "test";
    }

    public Properties getProperties() {
        return buildDefaults();
    }

    private Properties buildDefaults() {
        Properties properties = new Properties();

        properties.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_PLAINTEXT");
        properties.put(SaslConfigs.SASL_MECHANISM, "PLAIN");

        properties.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, "jishuceshi6:9092");
        return properties;
    }

    public String getTopic() {
        return topic;
    }

    public String getConsumerGroup() {
        return consumerGroup;
    }
}
