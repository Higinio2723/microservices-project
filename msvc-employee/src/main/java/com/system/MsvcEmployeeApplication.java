package com.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.system.controller", "com.system.service", "com.system.repository"})
public class MsvcEmployeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcEmployeeApplication.class, args);
	}

}
