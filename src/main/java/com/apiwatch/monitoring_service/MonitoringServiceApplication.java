package com.apiwatch.monitoring_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableFeignClients
@EnableScheduling
public class MonitoringServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonitoringServiceApplication.class, args);
	}

}
