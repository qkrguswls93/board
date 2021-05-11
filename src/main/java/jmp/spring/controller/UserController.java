package jmp.spring.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jmp.spring.service.UserService;
import jmp.spring.vo.User;
import lombok.extern.log4j.Log4j;

@Log4j
@Controller
public class UserController {
	
	@Autowired
	public UserService service;
	
	@GetMapping("/login")
	public void login() {
		
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest req) {
		HttpSession session = req.getSession();
		session.invalidate();
		
		return "/login";
	}
	
	@PostMapping("/loginAction")
	public String loginAction(User vo, Model model, HttpServletRequest req) {
		
		User user = service.login(vo);
		
		if(user == null) {
			model.addAttribute("msg", "로그인 실패했습니다.\n ID/PW 를 확인하세요");
			return "/login";
		}else {
			//user객체를 세션에 담는다-로그인처리
			HttpSession session = req.getSession();
			session.setAttribute("user", user);
			log.info("======="+user);
			model.addAttribute("msg", user.getId()+"님 환영합니다.");
			model.addAttribute("user", user);
			return "/loginAction";
		}
	}
	
	
	
}
