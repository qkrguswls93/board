package jmp.spring.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import jdk.internal.org.jline.utils.Log;
import jmp.spring.service.UserService;
import jmp.spring.vo.MenuVo;
import jmp.spring.vo.User;
import lombok.extern.log4j.Log4j;

@Log4j
public class MenuInterceptor extends HandlerInterceptorAdapter{
	
	@Autowired
	UserService service;
	/*
	 * This implementation always returns {@code true}.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception { //컨트롤러 실행 전이기 때문에 여기에 만든다
		
		log.info("menuInterceptor=========================");
		
		//세션에 메뉴가 없으면 
		HttpSession session = request.getSession();
		if(session.getAttribute("menu") == null) {
			List<MenuVo> list = service.getMenu();
			session.setAttribute("menu", list);
		}
		return true;
	}

	/**
	 * This implementation is empty.
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable ModelAndView modelAndView) throws Exception {
		
	}
}
