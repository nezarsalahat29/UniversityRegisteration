package com.school.crud.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Registeration System",
				description = "An API system for a School with registration system",
				version = "v1.0",
				contact = @Contact(
						name = "Nezar Al-Salahat",
						email = "Nizar.Salahat@Globitel.com"
						)
				),
		externalDocs = @ExternalDocumentation(
				description = "Resgisteration System"
				)
		
		)

public class SpringBootCrudExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootCrudExampleApplication.class, args);
	}

}
