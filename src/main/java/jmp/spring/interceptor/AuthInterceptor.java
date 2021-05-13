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
import lombok.extern.log4j.Log4j;

@Log4j
public class AuthInterceptor extends HandlerInterceptorAdapter{

	@Autowired
	UserService service;
	/*
	 * This implementation always returns {@code true}.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//로그인된 사용자만 허용=세션을 가지고 와서 유저객체가 있는지 확인
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		//만약 유저객체가 널이면 로그인하지않은 사용자가 접근
		//자동로그인->세션에 user객체를 넣어준다
		//자동로그인이 가능한 사용자라면->로그인처리해준다
		if(user == null) {
			//자동로그인이 가능한 사용자인지 판단
			Cookie loginCookie = WebUtils.getCookie(request, "loginCookie"); //쿠키는 웹유틸로부터 가지고 온다->브라우저에 저장된 쿠키를 가져올수있다
			
			//자동로그인사용자이다.
			if(loginCookie != null) {
			user = service.loginSessionkey(loginCookie.getValue());
				if(user != null) {
					//로그인처리 세션에 유저객체 생성
					session.setAttribute("user", user);
					}
				
					
				}
		}
		
		
		//로그인 확인, 권한확인
		//로그인 여부?
		if( user != null) {
			if(user.hasRole("ROLE_USER")) {
				//로그인이 되어있고 권한이 있는 자만이 board/list접근 가능
				return true;
			}
		} 	
		//만약 로그인을 안했거나 권한이 없다면 로그인페이지로 이동
		//원래 요청했던 페이지와 파라미터를 세션에 저장한다.
		System.out.println("uri=============="+request.getRequestURI());
		System.out.println("query============="+request.getQueryString());
		String uri = request.getRequestURI(); //요청했던 정보의 uri 사용자가 넘긴 정보는 request에 쿠키는 배열로 나온다
		String query = request.getQueryString(); //요청했던 정보의 파라미터
		
		if(query != null) {
			uri += "?"+query;
		}
		session.setAttribute("tmpUri", uri);
		
			response.sendRedirect("/login"); //로그인안된사용자는 로그인페이지로 이동
			return false;
		
													 		
	}

	/**
	 * This implementation is empty.
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable ModelAndView modelAndView) throws Exception {
		
	}
}
