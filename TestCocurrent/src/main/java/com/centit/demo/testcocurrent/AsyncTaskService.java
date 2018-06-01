package com.centit.demo.testcocurrent;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AsyncTaskService {

    @Async
    public void dataTranslate(int i)
    {
        log.info("启动了线程"+i);
    }

}
