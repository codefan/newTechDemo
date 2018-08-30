package com.centit.demo.kafka;

import com.centit.demo.JavaKafkaConfigurer;
import com.centit.support.algorithm.BooleanBaseOpt;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.common.config.SaslConfigs;

import java.util.Properties;

public class KafkaConfig {

    public KafkaConfig(){
    }

    public Properties getProperties() {
        return buildDefaults();
    }

    private Properties buildDefaults() {
        Properties properties = new Properties();
        Properties props = JavaKafkaConfigurer.getKafkaProperties();
        properties.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, props.getProperty("kafka.bootstrap.servers"));

        if(BooleanBaseOpt.castObjectToBoolean(props.getProperty("java.security.auth.enable"),false)) {
            properties.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_PLAINTEXT");
            properties.put(SaslConfigs.SASL_MECHANISM, "PLAIN");
        }else {

            properties.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "PLAINTEXT");
         }
        return properties;
    }

    public String getTopic() {
        return JavaKafkaConfigurer.getKafkaProperties().getProperty("kafka.topic");
    }

    public String getConsumerGroup() {
        return JavaKafkaConfigurer.getKafkaProperties().getProperty("kafka.group.id");
    }
}
