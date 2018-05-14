package com.centit.demo.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/**
 * 参见文档
 * https://blog.csdn.net/lianjunzongsiling/article/details/52622864
 */
public class MessageConsumer {
    private static final Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

    private final KafkaConfig config;

    private ExecutorService executor;

    private final AtomicBoolean running = new AtomicBoolean();

    private CountDownLatch stopLatch;

    private KafkaConsumer<String, String> consumer;

    private Consumer<DemoMessage> realConsumer;


    public MessageConsumer(KafkaConfig config,Consumer<DemoMessage> realConsumer ) {
        this.config = config;
        this.realConsumer = realConsumer;
    }


    public void start() throws Exception {
        executor = Executors.newSingleThreadExecutor();
        executor.submit(this::loop);
        running.set(true);
        stopLatch = new CountDownLatch(1);
    }

    private void loop() {
        logger.info("starting");
        Properties properties = config.getProperties();
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, config.getConsumerGroup());
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        consumer = new KafkaConsumer<>(properties);
        List<String> toptics = new ArrayList<>(4);
        toptics.add(config.getTopic());
        consumer.subscribe(toptics);
        logger.info("started");

        do {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                logger.debug("offset={}, key={}, value={}", record.offset(), record.key(), record.value());
                DemoMessage message = new DemoMessage(record.value(), config.getTopic(), record.partition(), record.offset());
                //业务处理
                realConsumer.accept(message);
                consumer.commitSync();

            }
        } while (running.get());

        logger.info("closing consumer");
        consumer.close();
        stopLatch.countDown();
    }

    public void stop() throws Exception {
        logger.info("stopping");
        running.set(false);
        stopLatch.await();
        executor.shutdown();
        logger.info("stopped");
    }
}
