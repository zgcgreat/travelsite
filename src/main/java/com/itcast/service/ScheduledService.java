package com.itcast.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Joy
 * /5 * * * ? 0/5 * * * * *
 */
@Slf4j
@Component
public class ScheduledService {
    @Scheduled(cron = "0/30 * * * * *")
    public void scheduled(){
//        for (int i = 0; i < 1000000; i++) {
//            System.out.println("定时任务1");
//        }
        log.info("=====>>>>>定时任务1，使用cron  {}",System.currentTimeMillis());
    }
    @Scheduled(fixedRate = 5000)
    public void scheduled1() {
//        for (int i = 0; i < 1000000; i++) {
//            System.out.println("定时任务2");
//        }
        log.info("=====>>>>>定时任务2，使用fixedRate{}", System.currentTimeMillis());
    }
    @Scheduled(fixedDelay = 5000)
    public void scheduled2() {
//        for (int i = 0; i < 1000000; i++) {
//            System.out.println("定时任务3");
//        }
        log.info("=====>>>>>定时任务3，fixedDelay{}",System.currentTimeMillis());
    }
}
