package locadora_api_java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LocadoraApiJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocadoraApiJavaApplication.class, args);
	}

}
