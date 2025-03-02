package com.workerp.hr_app_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class HrAppServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(HrAppServiceApplication.class, args);
	}
}
