package uy.com.pepeganga.userservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api")
public class UserController {

	
	@GetMapping
	public ResponseEntity<String> getExample(){
		return new ResponseEntity<>("User Service Working Successfully", HttpStatus.OK);
	}
}
