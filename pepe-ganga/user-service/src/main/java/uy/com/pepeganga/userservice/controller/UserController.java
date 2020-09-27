package uy.com.pepeganga.userservice.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import com.netflix.ribbon.proxy.annotation.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uy.com.pepeganga.userservice.entities.Profile;
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
	
	@PostMapping("/users/save-user-profile")
	public ResponseEntity<Profile> saveUser(@RequestBody Profile profile){
		return new ResponseEntity<>(userService.saveUserProfile(profile), HttpStatus.CREATED);
	}

	@PutMapping("/users/update-user-profile/{profileId}/{userId}")
	public ResponseEntity<Profile> updateUserWithProfile(@RequestBody Profile profile, @PathVariable  Integer profileId, @PathVariable Integer userId){
		return new ResponseEntity<>(userService.updateUserProfile(profile, profileId, userId), HttpStatus.OK);
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<Void> updateUserWithProfile( @PathVariable Integer id){
		userService.deleteUser(id);
		return new ResponseEntity<>( HttpStatus.NO_CONTENT);
	}
	
	
}
