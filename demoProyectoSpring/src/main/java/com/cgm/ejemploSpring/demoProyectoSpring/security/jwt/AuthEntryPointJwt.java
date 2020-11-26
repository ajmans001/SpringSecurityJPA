package com.cgm.ejemploSpring.demoProyectoSpring.security.jwt;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


@Component
public class AuthEntryPointJwt
  implements AuthenticationEntryPoint
{
    
  public void commence(HttpServletRequest request, 
		  HttpServletResponse response, 
		  AuthenticationException authException) 
				  {
	  
    try {
    	System.out.println(authException.getMessage());
		response.sendError(401, "Error: Unauthorized");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
}
