package com.centit.demo.kafka;

import com.centit.demo.JavaKafkaConfigurer;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.concurrent.Future;

public class TestKafka {

    private static void testSendMessage()  throws Exception {
        MessageProducer producer = new MessageProducer(new KafkaConfig());
        producer.init();
        Future<RecordMetadata> f = producer.send("hello","Hello world from test!");
        System.out.println(f.isDone());
        producer.destory();
    }

    private static MessageConsumer consumer;

    private static void testRecvMessage()  throws Exception {
        consumer = new MessageConsumer(
            new KafkaConfig(),
            (msg) -> System.out.println(msg.getMessage()));
        consumer.init();
        consumer.start();
    }

    public static void main(String [] args) throws Exception {
        JavaKafkaConfigurer.configureSasl();
        //testSendMessage();
        testRecvMessage();
        int n = System.in.read();
        System.out.println("Now stop : " + n);
        consumer.stop();
        System.out.println("Done!");
    }
}
