package jmp.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import jmp.spring.service.BoardService;
import jmp.spring.vo.BoardVo;
import jmp.spring.vo.Criteria;

@RestController
public class RepalController {

	@Autowired
	BoardService service;
	
	@GetMapping("/reply/test")
	public ResponseEntity<List<BoardVo>> restTest() {
		Criteria cri = new Criteria();
		return new ResponseEntity<List<BoardVo>>(service.getList(cri),HttpStatus.OK);
		//return service.getList(cri);
				}
	
	@GetMapping("/reply/test/{pageNo}")
	public ResponseEntity<List<BoardVo>> restPathTest(
			@PathVariable("pageNo") int pageNo) {
		
		Criteria cri = new Criteria(pageNo, 20);
		return new ResponseEntity<List<BoardVo>>(service.getList(cri), HttpStatus.OK);
		//return service.getList(cri);
	}
}
