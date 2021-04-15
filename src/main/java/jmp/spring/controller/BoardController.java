package jmp.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jmp.spring.service.BoardService;
import jmp.spring.vo.BoardVo;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class BoardController {

	@Autowired
	BoardService service;
	
	@GetMapping({"/board/get","board/edit"})
	public void get(BoardVo vo, Model model) {
		log.info(vo.getBno());
		//상세화면조회
		log.info(vo.getBno());	
		model.addAttribute("vo",service.get(vo.getBno()));
		
		
	}
	
	//1. 등록페이지로 이동
	@GetMapping("/board/register")
	public void insert() {
	}
	
	@PostMapping("/board/register")
	public String insertExe(BoardVo vo, RedirectAttributes rttr) {
		log.info("=========="+vo);
		System.out.println("==========vo: "+ vo);
		
		int res = service.insertBoard(vo);
		log.info("=========="+vo);
		
		
		rttr.addFlashAttribute("resMsg", vo.getBno()+"번 게시글이 등록 되었습니다.");
		return "redirect:/board/list";
	}
	
	
	@GetMapping("/board/list")
	public void getList(Model model) {
		
		
		model.addAttribute("list",service.getList());
		
		log.info("getList()================");
	}
	
}
