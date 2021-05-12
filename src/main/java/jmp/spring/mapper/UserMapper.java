package jmp.spring.mapper;


import java.util.List;

import jmp.spring.vo.User;

public interface UserMapper {

	public User login(User user);
	
	public List<String> getRole(String id);
	
	public int updateSessionkey(User user);
	
	public User loginSessionkey(String sessionkey);


	
}