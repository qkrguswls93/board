package jmp.spring.board;

import java.util.UUID;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncode {

	@Test
	public void uuidTest() {
		//비밀번호찾기
		//입력된 아이디와 메일이 일치할경우 임시비번 생성
		//비번 암호화처리해놔서 그대로는 로그인 안돼
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println("uuid================="
				+ encoder.encode(UUID.randomUUID().toString().substring(0,7)));
	}
	
	@Test
	public void test() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		System.out.println("=============="+encoder.encode("1234"));

		System.out.println("=============="+encoder.matches("1234", encoder.encode("1234")));
	}
}
