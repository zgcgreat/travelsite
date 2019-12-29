package com.itcast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TravelsiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(TravelsiteApplication.class, args);
    }

}
