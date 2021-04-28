package jmp.spring.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Aspect
@Component
@Log4j
public class LogAdvice {
	//리턴타입, 클래스이름, 메서드이름 리턴타임은 생략 가능
			
			//pointcut
	@Before("execution(* jmp.spring.service.BoardService.*(..))")
	public void logBefore() {
		log.info("AOP=====================");
	}
	
	@Around("execution(* jmp.spring.service.BoardService.*(..))")
	public Object logTime(ProceedingJoinPoint pjp) {
		
		Object result = null;
		long start = System.currentTimeMillis();
		
		log.info(pjp.getTarget());
		log.info(Arrays.toString(pjp.getArgs()));
		
		try {
			result = pjp.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		long end = System.currentTimeMillis();
		log.info("실행시간 : " + (end-start)/1000.0 + "초");
		
		return result;
	}
	
}
