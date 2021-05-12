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

import jmp.spring.service.UserService;
import jmp.spring.vo.User;

public class LoginInterceptor extends HandlerInterceptorAdapter{
	
	@Autowired
	UserService service;

	/**
	 * This implementation always returns {@code true}.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		return true;
	}

	/**
	 * loginAction 컨드롤러가 실행된이후
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable ModelAndView modelAndView) throws Exception {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		System.out.println("======="+user);
		System.out.println("======="+request.getAttribute("useCookie"));
		
		//로그인 성공시 자동로그인을 위한 쿠키 생성
		//자동로그인 체크가 되었을때
		//StringUtils.isEmpty(request.getAttribute("userCookie"));
		if(user != null && request.getParameter("userCookie") != null) {			//자동로그인을 위한 쿠키생성
			
			//users테이블에 쿠키정보를 저장
			//session.getId를 sessionkey=세션아이디를 저장하기로 약속
			//loginCookie=자동로그인시생성하는쿠키
			//여기에 저장되는 value갑소가 usrs테이블의 sessionkey에 저장되는 값은 동일
			user.setSessionkey(session.getId());
			service.updateSessionkey(user);
						
			
			//자동로그인을 위해 생성한 쿠키를 response 객체에 저장
			Cookie loginCookie = new Cookie("loginCookie", session.getId());
			loginCookie.setMaxAge(60*60*24*7);
			loginCookie.setPath("/");
			
			response.addCookie(loginCookie);
			
			String tmpUri = (String)session.getAttribute("tmpUri");
			if(!StringUtils.isEmpty(tmpUri)) {
				response.sendRedirect(tmpUri);
			}
		}
	}

}
