package com.hotel.backend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class BackendApplication {
	@Value("${jwt.secretkey}")
	private String jwtSecretKey ;
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
	@PostConstruct
	public void init() {
		System.out.println("JWT Secret Key: " + jwtSecretKey);
	}

}
