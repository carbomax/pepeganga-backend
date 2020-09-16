package uy.com.pepeganga.userservice.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.github.javafaker.Faker;

import uy.com.pepeganga.userservice.entities.User;
import uy.com.pepeganga.userservice.models.UserFaker;
import uy.com.pepeganga.userservice.repository.UserRepository;


@Service
public class UserService implements IUserService {

	@Autowired
	Faker faker;
	
	
	@Autowired
	UserRepository repository;

	private List<UserFaker> users = new ArrayList<>();

	@PostConstruct
	public void init() {

		for (int i = 0; i < 100; i++) {
			users.add(new UserFaker(faker.funnyName().name(), faker.name().username(), faker.dragonBall().character()));
		}
	}

	@Override
	public List<UserFaker> getUsersFakers() {
		return this.users;
	}

	@Override
	public List<User> getAllUsers() {		
		return repository.findAll();
	}

	@Override
	public User saveUser(User user) {		
		return repository.save(user);
	}

	@Override
	public Page<User> getUsersPage(User filter, int page, int size) {			
		return repository.findAllByEmail("test", PageRequest.of(page, size));
	}
	
	

}
