package com.pratiti.soccbuzz;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SoccbuzzApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoccbuzzApplication.class, args);
	}

	@Bean
	public static ModelMapper modelMapper(){
		return new ModelMapper();
	}
}