package com.centit.demo.kafka;

import org.apache.kafka.clients.CommonClientConfigs;

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

        properties.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "PLAINTEXT");

        properties.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, "codefanpc:9092");
        return properties;
    }

    public String getTopic() {
        return topic;
    }

    public String getConsumerGroup() {
        return consumerGroup;
    }
}
