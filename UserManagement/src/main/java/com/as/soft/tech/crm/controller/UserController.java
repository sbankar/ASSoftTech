package com.as.soft.tech.crm.controller;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.xml.ws.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.as.soft.tech.crm.entity.User;
import com.as.soft.tech.crm.repository.UserRepository;

@RestController
public class UserController {

	@Autowired
	private UserRepository repository;

	@RequestMapping("/hello")
	public String sayHello() {
		return "Hello";
	}

	@RequestMapping("/users")
	public ResponseEntity<?> getUsers() {
		List<User> users = new ArrayList<User>();
		repository.findAll().forEach(users::add);
		return ResponseEntity.ok(users);
	}

	@RequestMapping("/user/{id}")
	public ResponseEntity<?> getUser(@PathVariable Long id) {
		Optional<User> optional = repository.findById(id);
		if (optional.isPresent()) {
			return ResponseEntity.ok(optional.get());
		} else {
			return ResponseEntity.ok("User not found!");
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/user")
	public ResponseEntity<?> addUser(@RequestBody User user) {
		repository.save(user);
		return ResponseEntity.ok("User Saved Successfully!");
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/user/{id}")
	public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
		Optional<User> optional = repository.findById(id);
		if (optional.isPresent()) {
			User user = optional.get();
			user.setEmail(updatedUser.getEmail());
			user.setFirstName(updatedUser.getFirstName());
			user.setLastName(updatedUser.getLastName());
			user.setPassword(updatedUser.getPassword());
			repository.save(user);
			return ResponseEntity.ok("Saved Successfully");
		} else {
			return ResponseEntity.ok("User not found!");
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/user/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable long id) {
		Optional<User> optional = repository.findById(id);
		if (optional.isPresent()) {
			repository.deleteById(id);
			return ResponseEntity.ok("Deleted Sucessfully!");
		} else {
			return ResponseEntity.ok("User not found!");
		}
	}

}
