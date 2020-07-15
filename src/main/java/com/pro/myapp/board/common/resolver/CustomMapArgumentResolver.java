package com.pro.myapp.board.common.resolver;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.pro.myapp.board.common.common.CommandMap;

public class CustomMapArgumentResolver implements HandlerMethodArgumentResolver{

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,	//바인딩된 클래스의 파라미터들을 수정하거나 값을 가져올수 있는데
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {		//여기서 CommandMap 클래스를 새로 만들어 파라미터 안의 값들을
																									//CommandMap에 할당하고 그값을 리턴
		CommandMap commandMap = new CommandMap();
		
		HttpServletRequest req = (HttpServletRequest)webRequest.getNativeRequest();
		Enumeration<?> enumeration = req.getParameterNames();
		
		String key = null;
		String[] value = null;
		while(enumeration.hasMoreElements()) {
			key = (String)enumeration.nextElement();
			value = req.getParameterValues(key);
			if(value!=null) {
				commandMap.put(key, (value.length>1)?value : value[0]);
			}
		}
		return commandMap;
	}
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {	//바인딩할 클래스를 지정해주는 곳으로 여기서 지정한 클래스를 resolveArgument 메소드에서 처리할수 있다.
		return CommandMap.class.isAssignableFrom(parameter.getParameterType());
	}
	
}
