package com.microservice.estudent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient	// Enable discovery client, no obligatoria pero recomendada
@SpringBootApplication
public class MicroserviceEstudentsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceEstudentsApplication.class, args);
	}

}
