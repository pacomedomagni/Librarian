package com.gcit.lms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan("com.gcit.lms")
@SpringBootApplication
public class LibrarianApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibrarianApplication.class, args);
	}
}
