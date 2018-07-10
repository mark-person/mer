package com.ppx.cloud.mer.index;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MerIndexController {
    
	@GetMapping("/")
	public void index(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    response.sendRedirect(request.getContextPath() + "/login/login");
	}
	
	
   
}