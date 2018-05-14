package com.centit.demo.kafka;

public class TestKafka {

    private static void testSendMessage()  throws Exception {
        MessageProducer producer = new MessageProducer(new KafkaConfig());
        producer.start();
        producer.send("hello","Hello world!");
        producer.stop();
    }

    private static MessageConsumer consumer = new MessageConsumer(
            new KafkaConfig(),
            (msg) -> System.out.println(msg.getMessage()));

    private static void testRecvMessage()  throws Exception {
       consumer.start();
    }

    public static void main(String [] args) throws Exception {
        testRecvMessage();
        int n = System.in.read();
        consumer.stop();
        System.out.print(n);
    }
}
