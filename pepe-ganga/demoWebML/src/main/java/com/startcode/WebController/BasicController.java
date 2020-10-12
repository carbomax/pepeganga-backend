package com.startcode.WebController;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.startcode.Models.User;
import com.startcode.Repository.IUserRepo;

@RestController
@RequestMapping("/User")
public class BasicController {

	 @Autowired
		IUserRepo userRepo;
		
		@GetMapping("/SearchAll")
		public List<User> ListAll(){
			return userRepo.findAll();		
		}
		
		@GetMapping("/FindById/{id}")
		public Optional<User> SearchById(@PathVariable("id") Integer id){
			return userRepo.findById(id);		
		}
		
		@GetMapping(path = "/SearchAllById/{ids}")
		public List<User> SearchAllById(@PathVariable("ids") List<Integer> ids){
			return userRepo.findAllById(ids);		
		}
		
		@PostMapping
		public User AddUser(@RequestBody User user){
			return userRepo.save(user);		
		}
		
		@DeleteMapping(value = "/{id}")
		public void DeletedUser(@PathVariable("id") Integer id){
			userRepo.deleteById(id);
		}
}
