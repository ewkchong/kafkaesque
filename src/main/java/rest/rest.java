package rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

@SpringBootApplication
public class rest {
	public static void main(String[] args) {
		String name = args.length >= 2 ? args[0] : "node-1";
		String port = args.length >= 2 ? args[1] : "8080";
		try {
			File file = new File("nodeinfo.txt");
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(name + "\n" + port);
			bw.close();
		} catch (Exception e) {
			System.out.println(e);
		}
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