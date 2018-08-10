package com.raja.smr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raja.smr.entity.User;
import com.raja.smr.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/name/{username}")
//	@Cacheable(value = "appuser", key = "'user_' + #username")
	public User findUser(@PathVariable("username") String username) {
		return userService.findUserByName(username);
	}
	
	@GetMapping("/all")
	@Cacheable(value = "appuser", key = "'all_user'")
	public List<User> allUser() {
		return userService.findUsers();
	}
}
