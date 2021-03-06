package jmp.spring.service;

import java.util.List;

import jmp.spring.vo.MenuVo;
import jmp.spring.vo.User;

public interface UserService {

	public User login(User user);
		
	public int updateSessionkey(User user);
	
	public User loginSessionkey(String sessionkey);

	public int insertUser(User user);
	
//	public User SearchId(User user);
		
//	public User Searchpwd(User user);
	
//	public int Updatepwd(User user);
	
	public User checkId(String id);

	public List<MenuVo> getMenu();

	public String SearchId(User vo);

	public User Searchpwd(User vo);

	public int Updatepwd(User user);



}
