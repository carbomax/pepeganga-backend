package uy.com.pepeganga.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.com.pepeganga.business.common.entities.Profile;
import uy.com.pepeganga.business.common.entities.User;
import uy.com.pepeganga.userservice.service.IUserService;

import java.util.List;


@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private IUserService userService;
	
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
	public ResponseEntity<Void> deleteUser( @PathVariable Integer id){
		userService.deleteUser(id);
		return new ResponseEntity<>( HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/users/find-by-email")
	public ResponseEntity<User> findUserByEmail(@RequestParam String email){
			return new ResponseEntity<>(userService.findByEmail(email), HttpStatus.OK);
	}

	@PutMapping("/users/enable-or-disable/{id}")
	public ResponseEntity<User> findUserByEmail(@PathVariable Integer id, @RequestParam boolean enable){
		return new ResponseEntity<>(userService.enableOrDisable(id, enable), HttpStatus.OK);
	}

	@PutMapping("/users/update-user/{id}")
	public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable Integer id){
		return new ResponseEntity<>(userService.updateUser(user, id), HttpStatus.OK);
	}

}
