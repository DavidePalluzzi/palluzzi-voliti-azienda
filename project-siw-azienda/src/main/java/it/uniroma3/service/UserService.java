package it.uniroma3.service;


import org.springframework.stereotype.Service;

import it.uniroma3.model.User;

@Service
public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user);
	public void saveAdmin(User user);
}