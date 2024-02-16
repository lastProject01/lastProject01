package com.project.Enovel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class EnovelApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnovelApplication.class, args);
	}

}
