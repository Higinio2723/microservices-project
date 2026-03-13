package com.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.system.*"})
public class MsvcEmployeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcEmployeeApplication.class, args);
	}

}
