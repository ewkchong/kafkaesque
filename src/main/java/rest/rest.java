package rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class rest {
	public static void main(String[] args) {
		KafkaRestController rest = new KafkaRestController();
		SpringApplication.run(rest.class, args);
	}

//	@GetMapping("/hello")
//	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
//
//		String containerAddress = "http://node-1:8080/testing";
//
//		RestTemplate restTemplate = new RestTemplate();
//		ResponseEntity<String> response = restTemplate.getForEntity(containerAddress, String.class);
//
//		if (response.getStatusCode().is2xxSuccessful()) {
//			String responseBody = response.getBody();
//			System.out.println("Response: " + responseBody);
//		} else {
//			System.out.println("Request failed with status code: " + response.getStatusCodeValue());
//		}
//
//		return String.format("Hello %s!", name);
//	}
//
//	@GetMapping("/testing")
//	public String testing(HttpServletRequest request) {
//		return String.format("THIS IS A RESPONSE FROM " + request.getServerName() + "!!!");
//	}`
}