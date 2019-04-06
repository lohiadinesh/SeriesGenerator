package com.nisum.seriesgenerator;

import javax.inject.Inject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.nisum.seriesgenerator.service.SeriesGeneratorService;

/**
 * Spring boot application starting point.
 * 
 * @author Dinesh Kumar Lohia
 * @email lohiadinesh@gmail.com
 *
 */

@Configuration
@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
public class Application extends SpringBootServletInitializer implements CommandLineRunner {

	@Inject
	private ApplicationContext context;

	@Inject
	private SeriesGeneratorService seriesGeneratorService;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		seriesGeneratorService.exclusions("/rawRanges.txt");
	}

	public ApplicationContext getContext() {
		return context;
	}

	public void setContext(ApplicationContext context) {
		this.context = context;
	}
}
