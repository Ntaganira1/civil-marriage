package com.civilMarriageSystem;

import com.civilMarriageSystem.Services.FilesStorageService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class CivilMarriageRegistrationApplication implements CommandLineRunner {
	@Resource
	FilesStorageService storageService;

	public static void main(String[] args) {
		SpringApplication.run(CivilMarriageRegistrationApplication.class, args);
	}
	@Override
	public void run(String... arg) throws Exception {
		storageService.init();
	}
}
