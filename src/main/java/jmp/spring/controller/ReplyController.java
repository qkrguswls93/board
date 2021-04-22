package jmp.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jmp.spring.service.ReplyService;
import jmp.spring.vo.BoardVo;
import jmp.spring.vo.ReplyVo;

@RestController //객체 반환
public class ReplyController {

	@Autowired
	ReplyService service;
	
	@GetMapping("/reply/list/{bno}")//bno값을 url로 받는다
	public List<ReplyVo> getList(@PathVariable("bno") int bno) {
		List<ReplyVo> list = service.getList(bno);
		
		return list;
	}
	
//	@Autowired
//	BoardService service;
//	
//	@GetMapping("/reply/test")
//	public ResponseEntity<List<BoardVo>> restTest() {
//		Criteria cri = new Criteria();
//		return new ResponseEntity<List<BoardVo>>(service.getList(cri),HttpStatus.OK);
//		//return service.getList(cri);
//				}
//	
//	@GetMapping("/reply/test/{pageNo}")
//	public ResponseEntity<List<BoardVo>> restPathTest(
//			@PathVariable("pageNo") int pageNo) {
//		
//		Criteria cri = new Criteria(pageNo, 20);
//		return new ResponseEntity<List<BoardVo>>(service.getList(cri), HttpStatus.OK);
//		//return service.getList(cri);
//	}
}
