package com.fatec.sig1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class Sig1Application {

	public static void main(String[] args) {
		SpringApplication.run(Sig1Application.class, args);
	}

}
