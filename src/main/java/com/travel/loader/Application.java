package com.travel.loader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.travel.model.Tag;
import com.travel.repositories.TagRepository;

@Configuration
@EnableAutoConfiguration
@EnableScheduling
@ComponentScan("com.travel")
@PropertySource("classpath:application.properties")
public class Application extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	public static void main(String... args) {
		System.setProperty("spring.profiles.default",
				System.getProperty("spring.profiles.default", "dev"));
		final ApplicationContext applicationContext = SpringApplication.run(
				Application.class, args);
		System.out.println("Startup Date: "
				+ applicationContext.getStartupDate());
		// Adding fixed data. DB INSERTS. This is working as we are dropping and
		// creating db each time.
		TagRepository tagRepository = applicationContext
				.getBean(TagRepository.class);
		tagRepository.save(new Tag("Adventure"));
		tagRepository.save(new Tag("Hill Station"));
		tagRepository.save(new Tag("Night Life"));
		tagRepository.save(new Tag("Nature"));
		tagRepository.flush();
	}

}
