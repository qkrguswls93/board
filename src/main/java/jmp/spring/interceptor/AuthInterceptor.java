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
		if(user == null) {
			//자동로그인이 가능한 사용자인지 판단
			Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");
			if(loginCookie != null) {
			user = service.loginSessionkey(loginCookie.getValue());
			session.setAttribute("user", user);
		}
		}
		
		
		if( user != null) {
			if(user.hasRole("ROLE_USER")) {
				return true;
			}else{ //로그인안된사용자는 로그인페이지로 이동	
				System.out.println(request.getRequestURI());
				System.out.println(request.getQueryString());
				
				String tmpUri = request.getRequestURI();
				String queryString = request.getQueryString();
				
				if(!StringUtils.isEmpty(queryString)) {
					tmpUri += "?"+queryString;
				}
				session.setAttribute("tmpUri", tmpUri);
				
				response.sendRedirect("/login");
				return false;
			}
		}else{ //로그인안된사용자는 로그인페이지로 이동		
			response.sendRedirect("/login");
			return false;
		}
													 		
	}

	/**
	 * This implementation is empty.
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable ModelAndView modelAndView) throws Exception {
		
	}
}
