package nl.rollingsticks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import nl.rollingsticks.domain.Storage;

@SpringBootApplication
@EnableConfigurationProperties(Storage.class)
public class RollingsticksApplication {

	public static void main(String[] args) {
		SpringApplication.run(RollingsticksApplication.class, args);
	}
}
