package jmp.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jmp.spring.mapper.UserMapper;
import jmp.spring.vo.User;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserMapper mapper;

	@Override
	public User login(User user) {
		// TODO Auto-generated method stub
		User loginUser = mapper.login(user);
		//로그인 성공시 권한을 조회
		if(loginUser != null) {
			List<String> role = mapper.getRole(loginUser.getId());
			//유저 객체를 조회해서 담아준다
			loginUser.setRole(role);
		}
		return loginUser;
	}

	@Override
	public List<String> getRole(String id) {
		// TODO Auto-generated method stub
		return mapper.getRole(id);
	}
	
	


}
