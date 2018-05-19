package com.centit.demo.kafka;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@SpringBootApplication
public class DemoApplication {

	private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

	private static DaemonProcess realProcess = new DaemonProcess(
			(i) -> {
				if(i % 10 ==0 ) {
					logger.info("Step : " + i);
				}
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} );



	public static void main(String[] args) throws Exception {
		//SpringApplication.run(DemoApplication.class, args);

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
