package jmp.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
			//비번비교 로직추가
			//비번일치하면 권한조회해서 유저객체 반환
			//비번불일치시 유저객체 null로 반환
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			//입력된값과 기존값이 같은지 확인
			//화면으로부터 입력받은 id/pw가 들어있는 객체, db에서 아이디를 조회해온 객체->암호화된 비번이 들어있음
			if(!encoder.matches(user.getPwd(), loginUser.getPwd())){
				return null;
			}	
			
			List<String> role = mapper.getRole(loginUser.getId());
			//유저 객체를 조회해서 담아준다
			loginUser.setRole(role);
		}
		return loginUser;
	}

	@Override
	public int updateSessionkey(User user) {
		// TODO Auto-generated method stub
		return mapper.updateSessionkey(user);
	}

	@Override
	public User loginSessionkey(String sessionkey) {
		// TODO Auto-generated method stub
		User user = mapper.loginSessionkey(sessionkey);
		List<String> role = mapper.getRole(user.getId());
		//유저 객체에 담아준다
		user.setRole(role);
		return user;
	}

	@Override
	public int insertUser(User user) {
		// TODO Auto-generated method stub
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		//암호화된 문자열 확인 비번 유저객체가 가지고 있다 비번 encode에 담아준다
		String encode = encoder.encode(user.getPwd());
		user.setPwd(encode);
		//사용자 추가
		int res = mapper.insertUser(user);
		//권한 추가
		if(res>0) {
			res = mapper.insertUserRole(user.getId(), "ROLE_USER");
		}
		return res;
	}

	@Override
	public User SearchId(User user) {
		// TODO Auto-generated method stub
		return mapper.SearchId(user);
	}

	@Override
	public User Searchpwd(User user) {
		// TODO Auto-generated method stub
		return mapper.Searchpwd(user);
	}

	@Override
	public int Updatepwd(User user) {
		// TODO Auto-generated method stub
		return mapper.Updatepwd(user);
	}

	


	
	
	


}
