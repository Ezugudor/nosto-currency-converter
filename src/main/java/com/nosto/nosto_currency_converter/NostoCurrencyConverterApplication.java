package com.nosto.nosto_currency_converter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import io.github.cdimascio.dotenv.Dotenv;

@EnableCaching
@SpringBootApplication
public class NostoCurrencyConverterApplication {

	public static void main(String[] args) {
		// Dotenv.configure().load();
		SpringApplication.run(NostoCurrencyConverterApplication.class, args);
	}

	@Bean
	public Dotenv dotenv() {
		return Dotenv.configure().load();
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

}
