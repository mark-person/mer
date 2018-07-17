package com.ppx.cloud.mer.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

import com.ppx.cloud.auth.common.AuthInterceptor;
import com.ppx.cloud.common.config.CommonMvcConfig;
import com.ppx.cloud.monitor.config.MonitorInterceptor;  



@Configuration
public class MerMvcConfig extends CommonMvcConfig {
	

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		super.addViewControllers(registry);
	}	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		super.addInterceptors(registry);
		registry.addInterceptor(new MonitorInterceptor()).excludePathPatterns("/static/**/*", "/favicon.ico");
		registry.addInterceptor(new AuthInterceptor()).excludePathPatterns("/static/**/*", "/favicon.ico");
	}
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
    }
	
	
	
	
	
	
	
}  
