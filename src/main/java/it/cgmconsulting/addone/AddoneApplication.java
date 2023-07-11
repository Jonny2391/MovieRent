package it.cgmconsulting.addone;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Addone Video store API", version = "3.0", description = "Addone Video store"))
public class AddoneApplication {

	public static void main(String[] args) {
		SpringApplication.run(AddoneApplication.class, args);
	}

}
