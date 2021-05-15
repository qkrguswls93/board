package jmp.spring.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import jmp.spring.service.MailService;
import jmp.spring.service.UserService;
import jmp.spring.vo.User;
import lombok.extern.log4j.Log4j;
import oracle.jdbc.proxy.annotation.Post;

@Log4j
@Controller
public class UserController {
	
	@Autowired
	public MailService mailservice;
	
	@Autowired
	public UserService service;
	
	@GetMapping("/login")
	public void login() {
		
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest req, HttpServletResponse res) {
		HttpSession session = req.getSession();
		session.invalidate();
		
		//자동로그인쿠키를 제거
		//로그아웃을하게되면 자동로그인 불가
		Cookie loginCookie = WebUtils.getCookie(req, "loginCookie");
		if(loginCookie != null) {
			 
			loginCookie.setMaxAge(0); //유효기간이 0이되면서 삭제된다
			loginCookie.setPath("/");
			
			res.addCookie(loginCookie);
		}
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
	
	@GetMapping("member")
	public void member() {
		
	}
	@PostMapping("/registerMember")
	public String registerMember(User user) {
		//회원가입처리
		try {
			//오류페이지이동
			//회원가입성공했으면 /logAction forward
			int res = service.insertUser(user);
			if(res>0) {
				return "forward:/loginAction";
			}else {
				return "/error";
			}
		}catch(Exception e) {
			e.printStackTrace();
			return "/error";
		}
	}
	@GetMapping("/SearchId")
	public void SearchId() {
		
	}
	@PostMapping("/findId")
	public String findId(User vo, Model model) {
		//여기에 알럿이랑 구현하면됨
		//일치하면 알럿으로아이디나오고로그인화면
		//불일치하면 불일치알럿
		User user = service.SearchId(vo);
		try {
		if(user == user) {
		
			model.addAttribute("msg", "아이디는 " +user.getId()+"입니다.");
			model.addAttribute("user", user);
			return "/login";
		}else {
			//user객체를 세션에 담는다-로그인처리
			model.addAttribute("msg", "이름/메일이 일치하지않습니다.");
			return "/SearchId";
			
		}
		}catch(Exception e) {
			e.printStackTrace();
			return "/error";
			
		}
		
	}
	@GetMapping("/Searchpwd")
	public void Searchpwd() {
		
	}
	@PostMapping("/findpwd")
	public String findpwd(User user, Model model) {	

		 	try {
		 	
			if(user == user) {
				model.addAttribute("msg", user.getEmail()+"로 임시비밀번호를 발급하였습니다.");
				
				  mailservice.passwordMailSend();
								 
				return "/login";
				
			}else {
				return "/findpwd";
			}
		}catch(Exception e) {
			e.printStackTrace();
			return "/error";
			
		}
		
		}
	}
	
	
	
	
	

