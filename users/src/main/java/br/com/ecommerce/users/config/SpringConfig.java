package br.com.ecommerce.users.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

	@Bean
	ModelMapper createModelMapper() {
		return new ModelMapper();
	}
}