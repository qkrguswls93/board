package jmp.spring.board;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import jmp.spring.mapper.AttachFileMapper;
import jmp.spring.mapper.ReplyMapper;
import jmp.spring.service.AttachFileService;
import jmp.spring.service.ReplyService;
import jmp.spring.vo.AttachFileVo;
import jmp.spring.vo.ReplyVo;
import lombok.extern.log4j.Log4j;

@Log4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")

@Service
public class AttachFileServiceTest {
	
	@Autowired
	AttachFileService service;
	
	@Autowired
	AttachFileMapper mapper;
	
	@Test
	public void getSeqService() {
		log.info(service.getSeq());
	}
	
	@Test
	public void getListervice() {
		log.info(service.getList(1));
	}
	@Test
	public void insert() {
		AttachFileVo vo = new AttachFileVo();
		vo.setUuid(service.getSeq()+"");
	}
	
	
	
}
