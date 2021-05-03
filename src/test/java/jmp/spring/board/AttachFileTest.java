package jmp.spring.board;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import jmp.spring.mapper.AttachFileMapper;
import jmp.spring.mapper.ReplyMapper;
import jmp.spring.vo.AttachFileVo;
import jmp.spring.vo.ReplyVo;
import lombok.extern.log4j.Log4j;

@Log4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class AttachFileTest {
	
	@Autowired
	AttachFileMapper mapper;
	
	@Test
	public void getSeq(){
		log.info("========seq : " + mapper.getSeq());
	}
	@Test
	public void getList() {
		log.info(mapper.getList(1));
	}
	@Test
	public void insert() {
		AttachFileVo vo = new AttachFileVo();
		vo.setAttachNo(1);
		vo.setFileName("fileName=");
		vo.setFileType("N");
		vo.setUploadPath("uploadPath");
		vo.setUuid("uuu");
		
		mapper.insert(vo);
	}
}
