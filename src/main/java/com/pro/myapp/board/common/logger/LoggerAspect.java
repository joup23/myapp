package com.pro.myapp.board.common.logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect	//Aspect Bean 객체 선언
public class LoggerAspect {
	protected Log log  = LogFactory.getLog(LoggerAspect.class);
	static String name = "";
	static String type= "";
	
	//어드바이스를 적용하는 부분 PointCut으로 execution 명시자 표현식 사용
	@Around("execution(* com..controller.*Controller.*(..)) or execution(* com..service.*Impl.*(..)) or execution(* com..dao.*DAO.*(..))")
	public Object logPrint(ProceedingJoinPoint joinPoint) throws Throwable{
		type= joinPoint.getSignature().getDeclaringTypeName();
		
		if(type.indexOf("Controller") > -1) {
			name = "Controller \t: ";
		}
		else if(type.indexOf("Service")> -1) {
			name = "ServiceImpl \t: ";
		}
		else if(type.indexOf("DAO") > -1) {
			name = "DAO \t\t: ";
		}
		log.debug(name + type + "." + joinPoint.getSignature().getName() + "()");
		return joinPoint.proceed();
	}
}
