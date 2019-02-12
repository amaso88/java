package com.gateway.rest.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class GatewayRestServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayRestServiceApplication.class, args);
	}

}

