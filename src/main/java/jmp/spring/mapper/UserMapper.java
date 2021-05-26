package jmp.spring.mapper;


import java.awt.Menu;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import jmp.spring.vo.MenuVo;
import jmp.spring.vo.User;

public interface UserMapper {

	

	public User login(User user);
	
	public List<String> getRole(String id);
	
	/*
	 * 자동로그인을 위한 키값과 유효기간을 지정
	 * 로그인 사용자 아이디
	 * 세션ID
	 */
	public int updateSessionkey(User user);
	
	public User loginSessionkey(String sessionkey);
	
	public int insertUser(User user);
	
	public int insertUserRole(@Param("id") String id, 
							  @Param("role") String role);
	
	//public User SearchId(User user);
	
	//public User Searchpwd(User user);

	//public int Updatepwd(User user);
	
	public User checkId(String id);
	
	public List<MenuVo> getMenu();

	public String SearchId(User vo);

	public User Searchpwd(User vo);
	
	public int Updatepwd(User user);


	
}
