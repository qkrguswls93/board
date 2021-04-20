package jmp.spring.vo;

import lombok.Data;
import lombok.extern.log4j.Log4j;

@Data
public class PageNavi {
	int startPage;	//페이지네비게이션시작번호
	int endPage;	//페이지네비게이션끝번호
	boolean prev;	//이전페이지여부
	boolean next;	//끝페이지여부
	
	//페이지 정보
	Criteria cri;
	
	//게시물의 총 건수
	int total;
	
	//생성자
	public PageNavi(Criteria cri, int total) {
		this.cri = cri;
		this.total = total;
		
		//getPageNo=현재페이지번호
		endPage = (int)Math.ceil((cri.getPageNo()/10.0)) * 10; //한페이지당게시글10개를기준으로 생성
		startPage = endPage-9;
		
		//실제 끝페이지 ceil=올림처리.double형이라int로 변환
		int realEndPage = (int)Math.ceil((total*1.0)/cri.getAmount());
		
		prev = startPage > 1 ? true : false;	//true,false 생략가능
		next = realEndPage > endPage ? true : false;	//true,false 생략가능
		
		//게시물수에 맞춘 실제 페이지에 맞춰 재정의
		endPage = endPage>realEndPage ? realEndPage : endPage;
		
	}
}
