package jmp.spring.board;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import jmp.spring.mapper.ReplyMapper;
import jmp.spring.service.ReplyService;
import jmp.spring.vo.ReplyVo;
import lombok.extern.log4j.Log4j;

@Log4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")

@Service
public class ReplyServiceTest {
	
	@Autowired
	ReplyService service;
	
	@Autowired
	ReplyMapper mapper;
	
	@Test
	public void getTotalTest() {
		log.info(mapper.getTotal(300));
	}
	
	@Test
	public void mapperGetListTest() {
		log.info(service.getList(300));
	}
	@Test
	public void mapperGetTest() {
		log.info(service.get(300));
	}
	@Test
	public void mapperInsertTest() {
		ReplyVo vo = new ReplyVo();
		vo.setBno(300);
		vo.setReply("리플");
		vo.setReplyer("작성자");;
		
		int res = service.insert(vo);

		log.info(service.insert(vo));
	}
	@Test
	public void mapperUpdateTest() {
		ReplyVo vo = new ReplyVo();
		vo.setRno(6);
		vo.setReply("리플");
		vo.setReplyer("작성자");;
		
		int res = mapper.update(vo);

		log.info(service.update(vo));
	}
	@Test
	public void mapperDeleteTest() {
		log.info(service.getList(300));
	}
}
