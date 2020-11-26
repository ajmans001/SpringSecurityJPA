package com.cgm.ejemploSpring.demoProyectoSpring.security;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.cgm.ejemploSpring.demoProyectoSpring.security.services.UserDetailsImpl;

/**
 * @apiNote Clase customizada para implementar seguridad 
 * @author jcarmona
 *
 */
@Component("securityService")
public class SecurityService
{
  public boolean hasPermission(String auth) {
    Authentication authe = SecurityContextHolder.getContext().getAuthentication();
    UserDetailsImpl userDetails = (UserDetailsImpl)authe.getPrincipal();

    List<String> roles = (List<String>) userDetails.getAuthorities().stream().map(item -> item.getAuthority())
			.collect(Collectors.toList());
    
    boolean regreso = false;
    for(String rol : roles) {
    	System.out.println("ROL = " + rol);
    	if(rol.equals(auth)) {
    		regreso = true;
    	}
    }
    return true;
  }
}
