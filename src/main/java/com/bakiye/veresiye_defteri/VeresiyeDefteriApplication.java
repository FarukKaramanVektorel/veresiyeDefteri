package com.bakiye.veresiye_defteri;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VeresiyeDefteriApplication {

	public static void main(String[] args) {
		SpringApplication.run(VeresiyeDefteriApplication.class, args);
	}
	@Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
