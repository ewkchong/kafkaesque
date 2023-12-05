package rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {

		String containerAddress = "http://node-1:8080/testing";

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity(containerAddress, String.class);

		if (response.getStatusCode().is2xxSuccessful()) {
			String responseBody = response.getBody();
			System.out.println("Response: " + responseBody);
		} else {
			System.out.println("Request failed with status code: " + response.getStatusCodeValue());
		}

		return String.format("Hello %s!", name);
	}

	@GetMapping("/testing")
	public String testing(HttpServletRequest request) {
		return String.format("THIS IS A RESPONSE FROM " + request.getServerName() + "!!!");
	}
}
