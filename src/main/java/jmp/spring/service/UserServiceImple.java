package jmp.spring.service;

import org.springframework.beans.factory.annotation.Autowired;

import jmp.spring.mapper.UserMapper;
import jmp.spring.vo.User;

public class UserServiceImple implements UserService{

	@Autowired
	UserMapper mapper;

	@Override
	public User login(User user) {
		// TODO Auto-generated method stub
		return mapper.login(user);
	}
	
	


}
