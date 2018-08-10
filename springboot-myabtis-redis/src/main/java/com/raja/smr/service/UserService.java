package com.raja.smr.service;

import java.util.List;

import com.raja.smr.entity.User;

public interface UserService {

	public List<User> findUsers();
	
	public User findUserByName(String username);
}
