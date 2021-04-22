package jmp.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jmp.spring.service.BoardService;
import jmp.spring.vo.BoardVo;
import jmp.spring.vo.Criteria;
import jmp.spring.vo.PageNavi;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class BoardController {

	@Autowired
	BoardService service;
	
	@GetMapping("/board/RestTest")
	public void restTest(){
		
	}
	
	@GetMapping("/board/delete")
	public String deleteExe(Criteria cri, BoardVo vo, RedirectAttributes rttr) {
		int res = service.delete(vo.getBno());
		String resMsg = "";
		if(res>0 ) {
			resMsg=vo.getBno()+"번 게시글이 삭제되었습니다.";
			rttr.addFlashAttribute("resMsg",resMsg);
			rttr.addAttribute("pageNo", cri.getPageNo());
			rttr.addAttribute("type", cri.getType());
			rttr.addAttribute("keyword", cri.getKeyword());
			
			return "redirect:/board/list";
		}else {
			resMsg="삭제에 실패했습니다.";
			rttr.addFlashAttribute("resMsg",resMsg);

			rttr.addAttribute("bno",vo.getBno());
			
			return "redirect:/board/get?bno=vo.getBno()";
		}

	}
	
	//수정하기
	@PostMapping("/board/edit")
	public String editExe(Criteria cri, BoardVo vo, RedirectAttributes rttr) { //매개변수로 자동수집
		
		int res = service.update(vo);
		String resMsg = "";
		if(res>0 ) {
			resMsg="수정되었습니다.";
		}else {
			resMsg="수정을 실패했습니다.";
		}
		rttr.addAttribute("bno", vo.getBno());
		rttr.addAttribute("pageNo", cri.getPageNo());
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		
		rttr.addFlashAttribute("resMsg",resMsg);
		return "redirect:/board/get"; //수정 후 get으로 돌아간다. list로 보내려면 return "redirect:/board/list"로
	}
	
	//상세화면
	@GetMapping({"/board/get"})
	public String get(Criteria cri, BoardVo vo, Model model) {
		log.info("======="+vo.getBno());
		//상세화면조회
		log.info("======="+vo.getBno());	
		model.addAttribute("vo",service.get(vo.getBno()));
		
		return "/board/get_b";
	}
	
	@GetMapping({"board/edit"})
	public String edit(Criteria cri, BoardVo vo, Model model) {
		log.info("======="+vo.getBno());
		//상세화면조회
		log.info("======="+vo.getBno());	
		model.addAttribute("vo",service.get(vo.getBno()));
		
		return "/board/edit_b";
	}
	
	//1. 등록페이지로 이동
	@GetMapping("/board/register")
	public String insert() {
		return "/board/register_b";
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
	public String getList(Criteria cri, Model model) {
		
		
		model.addAttribute("list",service.getList(cri));
		model.addAttribute("pageNavi", new PageNavi(cri, service.getTotal(cri)));
		log.info("getList()================");
		
		return "/board/list_b";
	}
	
	@GetMapping("/board/reply")
	public void reply() {
		
	}
}
