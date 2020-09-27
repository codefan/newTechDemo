package com.centit.kubernetes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;

@SpringBootApplication(exclude = JacksonAutoConfiguration.class)
public class KubernetesApplication {

	public static void main(String[] args) {
		SpringApplication.run(KubernetesApplication.class, args);
	}

}
