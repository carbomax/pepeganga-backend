package uy.com.pepeganga.userservice.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uy.com.pepeganga.userservice.entities.User;
import uy.com.pepeganga.userservice.service.IUserService;


@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private IUserService userService;
	
	
	@GetMapping
	public ResponseEntity<String> getExample(){
		return new ResponseEntity<>("User Service Working Successfully", HttpStatus.OK);
	}
	
	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers(){
		return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
	}
	
	@PostMapping("/users")
	public ResponseEntity<User> saveUser(@RequestBody User user){
		return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
	}
	
	@GetMapping("/users/{page}/{size}")
	public Page<User> getUsersPage(@PathVariable int page, @PathVariable int size){			
		return userService.getUsersPage(new User(), page, size);
	}
	
	
	
}
