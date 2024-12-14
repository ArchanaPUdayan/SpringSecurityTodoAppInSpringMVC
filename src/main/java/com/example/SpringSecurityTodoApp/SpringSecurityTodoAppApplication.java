package com.example.SpringSecurityTodoApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.SpringSecurityTodoApp")
public class SpringSecurityTodoAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityTodoAppApplication.class, args);
	}

}
