package com.cgm.ejemploSpring.demoProyectoSpring.security.jwt;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import java.util.Calendar;
import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.cgm.ejemploSpring.demoProyectoSpring.security.services.UserDetailsImpl;


@Component
public class JwtUtils
{
  private static final Logger logger = 
		  LogManager.getLogger(JwtUtils.class);
  
  @Value("${back.app.jwtSecret}")
  private String jwtSecret;
  
  @Value("${back.app.jwtExpirationMs}")
  private int jwtExpirationMs;
  
  @Value("${back.app.jwtExpirationMsApp}")
  private long jwtExpirationMsApp;
  
  public String generateJwtToken(
		  Authentication authentication) {
    UserDetailsImpl userPrincipal = 
    		(UserDetailsImpl)authentication.getPrincipal();
    
    long expires = 0L;
    expires = this.jwtExpirationMs;
     
    return Jwts.builder()
      .setSubject(userPrincipal.getUsername())
      .setIssuedAt(Calendar.getInstance().getTime())
      .setExpiration(new Date(Calendar.getInstance()
    		  .getTime().getTime() + expires))
      .signWith(SignatureAlgorithm.HS512, this.jwtSecret)
      .compact();
  }
  
  public String getUserNameFromJwtToken(String token) {
    return ((Claims)Jwts.parser().setSigningKey(
    		this.jwtSecret).parseClaimsJws(token)
    		.getBody()).getSubject();
  }
  
  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(this.jwtSecret)
      .parseClaimsJws(authToken);
      return true;
    } catch (SignatureException e) {
      logger.error("Invalid JWT signature: {}"
    		  , e.getMessage());
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token: {}"
    		  , e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("JWT token is expired: {}"
    		  , e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("JWT token is unsupported: {}"
    		  , e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty: {}"
    		  , e.getMessage());
    } 
    
    return false;
  }
  
  public Date expireJWTToken(String authToken) {
    Date expires = null;
    try {
      expires = ((Claims)Jwts.parser()
    		  .setSigningKey(this.jwtSecret)
    		  .parseClaimsJws(authToken).getBody())
    		  .getExpiration();
    }
    catch (SignatureException e) {
      logger.error("Invalid JWT signature: {}"
    		  , e.getMessage());
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token: {}"
    		  , e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("JWT token is expired: {}"
    		  , e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("JWT token is unsupported: {}"
    		  , e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty: {}"
    		  , e.getMessage());
    } 
    
    return expires;
  }
}
