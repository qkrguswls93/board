package jmp.spring.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
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
	
	@PostMapping("/searchId")
	@ResponseBody
	public Map<String, String> searchId(@RequestBody User vo) {
		String id = service.SearchId(vo);
		Map<String, String> res = new HashMap<String, String>();
		if(StringUtils.isEmpty(id)) {
			res.put("msg", "이름/이메일에 일치하는 아이디가 없습니다.");
		}else {
			res.put("mag", "아이디는 " +id+"입니다.");
		}
		return res;
	}
	
	@PostMapping("/searchpwd")
	@ResponseBody //객체반환
	public Map<String, String> searchpwd(@RequestBody User vo) {
		Map<String, String> res = new HashMap<String, String>();
		User user = service.Searchpwd(vo);
		
		if(user != null) {
			//String pwd = UUID.randomUUID().toString().substring(0,7);
			int updateRes = service.Updatepwd(user);
			
			if(updateRes>0) {
				mailservice.passwordMailSend();
			}
			
		}else {
			res.put("msg", "일치하는 사용자가 없습니다.");
		}
		
		return res;
	}
	
	/*
	 * @GetMapping("/SearchId") public void SearchId() {
	 * 
	 * }
	 * 
	 * @PostMapping("/findId") public String findId(User vo, Model model) {
	 * //여기에알럿이랑 구현하면됨 //일치하면 알럿으로아이디나오고로그인화면 //불일치하면 불일치알럿 User user =
	 * service.SearchId(vo); try { if(user == user) {
	 * 
	 * model.addAttribute("msg", "아이디는 " +user.getId()+"입니다.");
	 * model.addAttribute("user", user); return "/login"; }else { //user객체를
	 * 세션에담는다-로그인처리
	 * 
	 * model.addAttribute("msg", "이름/메일이 일치하지않습니다."); return "/SearchId";
	 * 
	 * } }catch(Exception e) { e.printStackTrace(); return "/error";
	 * 
	 * } }
	 */
	
	/*
	 * @GetMapping("/Searchpwd") public void Searchpwd() {
	 * 
	 * }
	 * 
	 * @PostMapping("/findpwd") public String findpwd(User user, Model model) {
	 * 
	 * try {
	 * 
	 * User dbuser = service.Searchpwd(user); //내가 입력할user랑 dbuser가 같아야 한다.-비번을 찾아야
	 * 하니까 여기서 가져온다 System.out.println(user.getId()==dbuser.getId());
	 * System.out.println(dbuser.getId().equals(user.getId()));
	 * if(user.getId().equals(dbuser.getId())) { //입력할 값이랑 저장된 값이 같으면 조회한다.
	 * model.addAttribute("msg", user.getEmail()+"로 임시비밀번호를 발급하였습니다.");
	 * 
	 * String pwd = mailservice.passwordMailSend();
	 *  BCryptPasswordEncoder encoder =
	 * new BCryptPasswordEncoder(); //비번 암호화 
	 * user.setPwd(encoder.encode(pwd));
	 * //암호화된 비번으로 인코딩 service.Updatepwd(user); //비번 바꾸기 return "/login";
	 * 
	 * }else { return "/findpwd"; } }catch(Exception e) { e.printStackTrace();
	 * return "/error";
	 * 
	 * }
	 * 
	 * }
	 */
	
	@GetMapping("/checkId/{id}")
	@ResponseBody
	public boolean checkId(@PathVariable("id") String id) { //만약 string 으로 할거면 return "fail"/"success"이거로
		//아이디 중복 체크
		if(service.checkId(id) != null) {//path 에서 받은 id값
			//아이디 있음
			return false;
		}else {
			//아이디 없음
			return true;
		}
		
	}
	
	}
	
	
	
	
	

