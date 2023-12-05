package rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class rest {

	public static void main(String[] args) {
		KafkaRestController rests = new KafkaRestController();
		SpringApplication.run(rest.class, args);




	}




}
