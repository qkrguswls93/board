package jmp.spring.board;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import jmp.spring.mapper.UserMapper;
import jmp.spring.service.MailService;
import jmp.spring.vo.User;
import lombok.extern.log4j.Log4j;

@Log4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class UserTest {
	
	@Autowired
	public UserMapper usermapper;
	
	@Test
	public void searchidTest() {
		User user = new User();
		
		user.setName("고구마");
		user.setEmail("potato@test.com");
		System.out.println("============"+usermapper.SearchId(user));

	}
	
	@Test
	public void insertTest() {
		User user = new User();
		user.setId("insertid");
		user.setPwd("pwd");
		user.setName("name");
		user.setEmail("email");
		
		System.out.println("============"+usermapper.insertUser(user));
	}
	
	
	
	@Test
	public void userUpdataSessionkey() {
		User user = new User();
		user.setSessionkey("user01_sesssionkey");
		user.setId("user01");
		
		System.out.println("============"+usermapper.updateSessionkey(user));
		
		
		
	}
	
	@Test
	public void getRoleTest() {
		log.info("==========="+usermapper.getRole("user01"));
	}

	@Test
	public void userTest() {
		User user = new User();
		user.setId("user01");
		user.setPwd("1234");
		log.info("==========="+usermapper.login(user));
		
	}
	
	
}
