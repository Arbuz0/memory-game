package com.example.memory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.example.memory")
@EnableJpaRepositories(basePackages = "com.example.memory.repository")
public class MemoryApplication {
	public static void main(String[] args) {
		SpringApplication.run(MemoryApplication.class, args);
	}
}
