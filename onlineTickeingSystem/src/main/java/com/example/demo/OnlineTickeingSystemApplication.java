package com.example.demo;


import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class OnlineTickeingSystemApplication {
	
	private static final Logger log = LoggerFactory.getLogger(OnlineTickeingSystemApplication.class);
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(OnlineTickeingSystemApplication.class, args);
		log.info("in to the application");
	}

}
