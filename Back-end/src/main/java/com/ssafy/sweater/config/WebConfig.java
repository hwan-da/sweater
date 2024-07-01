package com.ssafy.sweater.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ssafy.sweater.interceptor.JwtInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	private JwtInterceptor jwtInterceptor;
	
	public WebConfig (JwtInterceptor jwtInterceptor) {
		this.jwtInterceptor = jwtInterceptor;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//자원 설정 
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
	}
	
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }
	
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	registry.addInterceptor(jwtInterceptor).addPathPatterns("/**")
    	.excludePathPatterns("/auth/**", "/chat/**");
    }
    
}
