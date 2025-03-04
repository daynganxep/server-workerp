package com.workerp.company_app_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients
public class CompanyAppServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompanyAppServiceApplication.class, args);
    }

}
