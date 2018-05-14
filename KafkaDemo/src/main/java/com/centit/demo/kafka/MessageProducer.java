package com.centit.demo.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.Future;

public class MessageProducer {
    private final KafkaConfig config;

    protected static final Logger logger = LoggerFactory.getLogger(MessageProducer.class);

    private Producer<String, String> producer;

    public MessageProducer(KafkaConfig config) {
        this.config = config;
    }

    public void start() throws Exception {
        logger.info("starting");
        Properties properties = config.getProperties();
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        properties.put(ProducerConfig.RETRIES_CONFIG, 0);
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        producer = new KafkaProducer<>(properties);
        logger.info("started");
    }

    public Future<RecordMetadata> send(String msgKey, String message) {
        return producer.send(new ProducerRecord<>(config.getTopic(), msgKey, message));
    }

    public void stop() throws Exception {
        logger.info("stopping");
        Producer<String, String> producer = this.producer;
        this.producer = null;
        logger.info("closing producer");
        producer.close();
        logger.info("stopped");
    }
}
