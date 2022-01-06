package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;

@SpringBootApplication
@EnableWebSecurity
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	DefaultSecurityFilterChain httpSecurity(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(requests -> requests
				.mvcMatchers("/user/**").hasRole("USER")
				.mvcMatchers("/admin/**").hasRole("ADMIN")
				.mvcMatchers("/public/**").permitAll()
				.anyRequest().denyAll()
			)
			.httpBasic(Customizer.withDefaults());
		return http.build();
	}
}
