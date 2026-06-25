package com.assignment.author_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.assignment.author_microservice.client")
@SpringBootApplication
@EnableDiscoveryClient
public class AuthorMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthorMicroserviceApplication.class, args);
	}

}
