package com.ssafy.sweater.interceptor;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ssafy.sweater.Util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor{
	private final String HEADER_AUTH = "access_token";
	
	private final JwtUtil jwtUtil;
	
	public JwtInterceptor(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String httpMethod = request.getMethod();
		
		System.out.println(request.getRequestURI());
		
		if(HttpMethod.GET.matches(httpMethod) || HttpMethod.OPTIONS.matches(httpMethod)) {
			return true;
		}
		
		else {
			//System.out.println(httpMethod);
			String token = request.getHeader("access_token");
			//System.out.println(token);
			if(token != null) {
				jwtUtil.validate(token);
				return true;
			}
			
		}

		throw new Exception("유효하지 않은 접근입니다.");
		
	}
	
}
