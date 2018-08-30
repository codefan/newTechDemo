package com.centit.demo.kafka;

import com.centit.demo.JavaKafkaConfigurer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@SpringBootApplication
public class KafkaConsumerDemoApplication {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerDemoApplication.class);

    private static MessageConsumer realProcess = new MessageConsumer(
        new KafkaConfig(),
        (msg) -> System.out.println(msg.getMessage()));


    public static void main(String[] args) throws Exception {
        JavaKafkaConfigurer.configureJavaSystemProps();
        SpringApplication.run(KafkaConsumerDemoApplication.class, args);
        realProcess.init();
        realProcess.start();

        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String inStr = br.readLine();
            while (!StringUtils.equalsIgnoreCase("exit", inStr)) {
                inStr = br.readLine();
            }
        }

        realProcess.stop();


        System.out.println("Done !");
    }
}
