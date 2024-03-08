package com.microservice.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer // Enable Spring Cloud Config Server
public class MicroserveConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserveConfigApplication.class, args);
	}

}
