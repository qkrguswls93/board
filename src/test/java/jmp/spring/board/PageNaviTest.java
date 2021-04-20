package jmp.spring.board;

import org.junit.Test;

import jdk.internal.org.jline.utils.Log;
import jmp.spring.vo.Criteria;
import jmp.spring.vo.PageNavi;
import lombok.extern.log4j.Log4j;

@Log4j
public class PageNaviTest {
	
	
	@Test
	public void pageNavi() {
		Criteria cri = new Criteria();	//criteria 생성
		cri.setPageNo(8);	//이거안하면 1-10으로 테스트됨
		
		PageNavi navi = new PageNavi(cri,150);	//임의로 총게시물 150개로
		
		log.info(navi);
	}
}
