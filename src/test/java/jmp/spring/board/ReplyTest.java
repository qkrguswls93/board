package jmp.spring.board;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import jmp.spring.mapper.ReplyMapper;
import jmp.spring.vo.ReplyVo;
import lombok.extern.log4j.Log4j;

@Log4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class ReplyTest {
	
	@Autowired
	ReplyMapper mapper;
	
	@Test
	public void mapperGetListTest() {
		log.info(mapper.getList(300));
	}
	@Test
	public void mapperGetTest() {
		log.info(mapper.get(300));
	}
	@Test
	public void mapperInsertTest() {
		ReplyVo vo = new ReplyVo();
		vo.setBno(300);
		vo.setReply("리플");
		vo.setReplyer("작성자");
		
		int res = mapper.insert(vo);

		log.info(mapper.insert(vo));
	}
	@Test
	public void mapperUpdateTest() {
		ReplyVo vo = new ReplyVo();
		vo.setRno(6);
		vo.setReply("목요일");
		vo.setReplyer("나");;
		
		int res = mapper.update(vo);

		log.info(mapper.update(vo));
	}
	@Test
	public void mapperDeleteTest() {
		log.info(mapper.getList(300));
	}
}
