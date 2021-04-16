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
	
	@GetMapping("/board/delete")
	public String deleteExe(BoardVo vo, RedirectAttributes rttr) {
		int res = service.delete(vo.getBno());
		String resMsg = "";
		if(res>0 ) {
			resMsg="삭제되었습니다.";
			return "redirect:/board/list";
		}else {
			resMsg="삭제에 실패했습니다.";
		}
		rttr.addAttribute("bno",vo.getBno());
		rttr.addFlashAttribute("resMsg",resMsg);
			return "redirect:/board/get?bno=vo.getBno()";

	}
	
	//수정하기
	@PostMapping("/board/edit")
	public String editExe(BoardVo vo, RedirectAttributes rttr) { //매개변수로 자동수집
		
		int res = service.update(vo);
		String resMsg = "";
		if(res>0 ) {
			resMsg="수정되었습니다.";
		}else {
			resMsg="수정을 실패했습니다.";
		}
		rttr.addAttribute("bno", vo.getBno());
		rttr.addFlashAttribute("resMsg",resMsg);
		return "redirect:/board/get"; //수정 후 get으로 돌아간다. list로 보내려면 return "redirect:/board/list"로
	}
	
	//상세화면
	@GetMapping({"/board/get","board/edit"})
	public void get(BoardVo vo, Model model) {
		log.info("======="+vo.getBno());
		//상세화면조회
		log.info("======="+vo.getBno());	
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
	
	//리스트
	@GetMapping("/board/list")
	public void getList(Model model) {
		
		
		model.addAttribute("list",service.getList());
		
		log.info("getList()================");
	}
	
}
