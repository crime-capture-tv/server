package com.mtvs.crimecapturetv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CrimecapturetvApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrimecapturetvApplication.class, args);
    }

}
