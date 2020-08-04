package com.cos.securityex01.test;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class GlobalExceptionContoller {
        
	@ExceptionHandler(value = {NullPointerException.class,IllegalArgumentException.class})
	public String 널_일리걸아규먼트_익센셥(Exception e) {
		StringBuilder sb = new StringBuilder();
		sb.append("<script>alert('"+e.getMessage()+"');");
		sb.append("location.href='/';</script>");
		
		return sb.toString();
	}
}
