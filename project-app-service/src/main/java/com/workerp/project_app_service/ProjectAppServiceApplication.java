package com.workerp.project_app_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ProjectAppServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProjectAppServiceApplication.class, args);
	}
}
