package com.example.engineering_construction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EngineeringConstructionApplication {

    public static void main(String[] args) {
        SpringApplication.run(EngineeringConstructionApplication.class, args);
    }

}
