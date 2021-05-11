package jmp.spring.vo;

import java.util.List;

import lombok.Data;

@Data
public class User {

	String id;
	String pwd;
	String name;
	String email;
	//사용여부
	String enabled;
	//권한리스트
	List <String> role;
	
	/*
	 * 권한의 여부 체크
	 * @param role_id
	 * @return
	 */
	//메소드 추가
	public boolean hasRole(String role_id) { //list가 role을 가지고 있는지 없는지 
		return role.contains(role_id);
	}
	
}
