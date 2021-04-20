package jmp.spring.vo;

import lombok.Data;

@Data //set, get메서드 자동 생성
public class Criteria {
	int pageNo;	//페이지 번호
	int amount;	//페이지당 게시물 수 
	String type;//검색타입
	String keyword;//검색키워드
	
	
	//초기화
	public Criteria(
			) {
		this.pageNo = 1;
		this.amount = 10;
	}
	
}
