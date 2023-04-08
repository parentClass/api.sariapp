package com.dnc.sariapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class SariapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SariapiApplication.class, args);
    }
}
