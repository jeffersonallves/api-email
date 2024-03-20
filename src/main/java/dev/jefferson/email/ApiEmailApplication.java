package dev.jefferson.email;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiEmailApplication {

	public static void main(String[] args) {

		SpringApplication.run(ApiEmailApplication.class, args);
		System.out.println("Email API Started");
	}

}
