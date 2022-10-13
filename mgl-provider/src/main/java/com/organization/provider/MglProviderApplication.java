package com.organization.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan({"com.organization.provider"})
public class MglProviderApplication extends SpringBootServletInitializer  {

	public static void main(String[] args) {
		SpringApplication.run(MglProviderApplication.class, args);
	}
}
