package jmp.spring.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import jmp.spring.service.UserService;
import jmp.spring.vo.User;

public class LoginInterceptor extends HandlerInterceptorAdapter{
	
	@Autowired
	UserService service;

	/**컨트롤러 실행 전
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		return true;
	}

	/**
	 * 처리조건?->user객체가 세션에 생성, 자동로그인에 체크되어있어야
	 * loginAction 컨트롤러가 실행된이후 자동로그인을 위한 쿠키 생성
	 * db에 세션ID값과 유효기간 저장 -> 저장된 자동로그인의 value값(sessionId값)은 users테이블에서 조회 일치값있으면 자동로그인 처리를 해준다 
	 * 세션/쿠키->request
	 * 저장후 user체이블에 sessionkey값이 저장되었는지 확인
	 * 브라우저에 loginCookie가 생성되었는지 확인
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable ModelAndView modelAndView) throws Exception {
		System.out.println("LoginInterceptor.post===========");
		
		//request로부터 세션을 구해온다
		HttpSession session = request.getSession();
		System.out.println("sessionID ===============>"+ session.getId());
		//Cookie[] cookie = request.getCookies()-이거 불편해서  이거씀->WebUtils.getCookie(request, "")
		
		User user = (User)session.getAttribute("user"); //controller의 session.setAttribute("user", user); 에서 세션을 user로 지정해뒀음
		
		System.out.println("=======user"+user);
		System.out.println("=======useCookie"+request.getAttribute("useCookie"));
		
		//로그인 성공시 자동로그인을 위한 쿠키 생성
		//자동로그인 체크가 되었을때
		//StringUtils.isEmpty(request.getAttribute("useCookie"));
		if(user != null && request.getParameter("useCookie") != null) {			//자동로그인을 위한 쿠키생성
			
			//users테이블에 쿠키정보를 저장
			//session.getId를 sessionkey=세션아이디를 저장하기로 약속
			//loginCookie=자동로그인시생성하는쿠키
			//여기에 저장되는 value값과 users테이블의 sessionkey에 저장되는 값은 동일
			//자동로그인을 위해 생성한 쿠키를 response 객체에 저장
			Cookie loginCookie = new Cookie("loginCookie", session.getId());
			loginCookie.setMaxAge(60*60*24*7); //60초 60분 24시간 7일 유효기간
			loginCookie.setPath("/"); //패쓰설정

			response.addCookie(loginCookie);

			//세션값 키값 넣기
			//자동로그인시 저장된 쿠키값을 db에서 조회한다
			user.setSessionkey(session.getId());
			System.out.println("sessionID ===============>"+ user.getSessionkey());
			service.updateSessionkey(user);
			response.addCookie(loginCookie);	
		}
			
			String tmpUri = (String)session.getAttribute("tmpUri"); //tmpuri 자동로그인 했을때 정보 저장해놨음 redirect 전에 삭제해준다.
			
			if(!StringUtils.isEmpty(tmpUri)) { 
				response.sendRedirect(tmpUri);
			}
		
	}

}
