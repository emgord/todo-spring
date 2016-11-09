package com.todo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class TodoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoApplication.class, args);
	}

	@Bean
	CommandLineRunner init(TodoRepository todoRepository) {
		return (evt) -> Arrays.asList(
				"Learn Java Spring,Find some more things to do".split(","))
				.forEach(
						action -> { todoRepository.save(new Todo(action, "description"));
						});
	}
}
