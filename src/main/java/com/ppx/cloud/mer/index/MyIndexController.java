package com.ppx.cloud.mer.index;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyIndexController {
	
	@Autowired
	private MyServiceImpl serv;
	
	@GetMapping("/")
	public void index(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    serv.test();
	    response.sendRedirect(request.getContextPath() + "/login/login");
	}
	
	
   
}