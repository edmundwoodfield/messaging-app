package com.example;

import io.swagger.v3.oas.models.media.Schema;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.Map;
import java.util.TreeMap;

@SpringBootApplication
public class BarclayMessageApplication {
	@Bean
	public OpenApiCustomizer sortSchemasAlphabetically() {
		return openApi -> {
			Map<String, Schema> schemas = openApi.getComponents().getSchemas();
			openApi.getComponents().setSchemas(new TreeMap<>(schemas));
		};
	}
	public static void main(String[] args) {

		SpringApplication.run(BarclayMessageApplication.class, args);
	}

}
