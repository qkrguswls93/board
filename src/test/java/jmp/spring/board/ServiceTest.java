package jmp.spring.board;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import jmp.spring.mapper.BoardMapper;
import jmp.spring.service.BoardService;
import jmp.spring.vo.BoardVo;
import lombok.extern.log4j.Log4j;

@Log4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")

public class ServiceTest {
	@Autowired
	BoardService service;
	
	@Autowired
	BoardMapper mapper;
	
	@Test
	public void deleteMapper() {
		int res = service.delete(308);
		
	}
	@Test
	public void updateMapper() {
		BoardVo vo = new BoardVo();
		vo.setBno(88);
		vo.setContent("내용-upmapperTest");
		vo.setTitle("제목 - upmapperTest");
		vo.setWriter("작성자 - upmapperTest");
		
		int res = mapper.update(vo);
		log.info("====="+res);

	}
	
	@Test
	public void getService() {
		BoardVo vo = service.get(88);
		log.info(vo);
	}
	@Test
	public void get() {
		BoardVo vo = mapper.get(88);
		
		log.info(vo);
	}
	
	
	@Test
	public void mapper() {
		BoardVo vo = new BoardVo();
		vo.setContent("내용-mapperTest");
		vo.setTitle("제목 - mapperTest");
		vo.setWriter("작성자 - mapperTest");
		
		int res = mapper.insertBoard(vo);
		
		//log.info("mapper.insert.test=========== : "+res);
		log.info("service.insertBoard=============:"+service.insertBoard(vo));
	}
	
	@Test
	public void service() {
		
		log.info("service==============="+service.getList());
	}
}
