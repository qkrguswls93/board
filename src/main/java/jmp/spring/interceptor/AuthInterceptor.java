package jmp.spring.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import jmp.spring.vo.User;
import lombok.extern.log4j.Log4j;

@Log4j
public class AuthInterceptor extends HandlerInterceptorAdapter{

	/*
	 * This implementation always returns {@code true}.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//로그인된 사용자만 허용=세션을 가지고 와서 유저객체가 있는지 확인
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		if( user != null) {
			if(user.hasRole("ROLE_USER")) {
				return true;
			}else{ //로그인안된사용자는 로그인페이지로 이동		
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
		log.info("postHandle=========================");
		log.info(request);
		log.info(response);
		log.info(handler);
		log.info(modelAndView);
		log.info("/postHandle=========================");
	}
}
