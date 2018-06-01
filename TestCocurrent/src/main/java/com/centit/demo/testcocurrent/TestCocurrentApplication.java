package com.centit.demo.testcocurrent;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class TestCocurrentApplication {

    public static void main(String[] args) {
        //SpringApplication.run(TestCocurrentApplication.class, args);
        AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(TestCocurrentApplication.class);
        AsyncTaskService service = context.getBean(AsyncTaskService.class);
        for(int i=0;i<10;i++){
            service.dataTranslate(i);
        }
    }
}
