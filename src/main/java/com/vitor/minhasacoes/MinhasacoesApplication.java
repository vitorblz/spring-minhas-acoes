package com.vitor.minhasacoes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class MinhasacoesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinhasacoesApplication.class, args);
	}

}