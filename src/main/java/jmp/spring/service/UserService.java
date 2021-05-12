package jmp.spring.service;

import java.util.List;

import jmp.spring.vo.User;

public interface UserService {

	public User login(User user);
		
	public int updateSessionkey(User user);
	
	public User loginSessionkey(String sessionkey);



}
