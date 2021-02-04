package ru.ananichev.crudBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "ru.ananichev.crudBoot")
public class CrudBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudBootApplication.class, args);
	}

}
