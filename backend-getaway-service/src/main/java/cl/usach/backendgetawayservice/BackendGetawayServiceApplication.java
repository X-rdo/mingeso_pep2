package cl.usach.backendgetawayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BackendGetawayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendGetawayServiceApplication.class, args);
	}

}
