package com.cgm.ejemploSpring.demoProyectoSpring.security.services;

import com.cgm.ejemploSpring.demoProyectoSpring.entities.CatUsuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl
  implements UserDetails
{
  private static final long serialVersionUID = 1L;
  private Integer id;
  private String username;
  private String email;
  private String nombre;
  private int status;
  @JsonIgnore
  private String password;
  private Collection<? extends GrantedAuthority> authorities;
  
  public UserDetailsImpl(Integer id, String username, 
		  String email, String password, String nombre, 
		  Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
    this.authorities = authorities;
    this.nombre = nombre;
  }





  
  public static UserDetailsImpl build(CatUsuario user) {
    List<GrantedAuthority> authorities = user.getRoles().stream().map(role -> 
    new SimpleGrantedAuthority(role.getNombre())).collect(Collectors.toList());
    return new UserDetailsImpl(user
        .getId_usuario(), user
        .getUsername(), user
        .getCorreo(), user
        .getPassword(), user
        .getNombre(), authorities);
  }


  
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  
  public Integer getId() {
    return this.id;
  }

  
  public String getEmail() {
    return this.email;
  }

  
  public String getNombre() {
    return this.nombre;
  }

  
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  
  public int getStatus() {
    return this.status;
  }

  
  public void setStatus(int status) {
    this.status = status;
  }


  
  public String getPassword() {
    return this.password;
  }


  
  public String getUsername() {
    return this.username;
  }


  
  public boolean isAccountNonExpired() {
    return true;
  }


  
  public boolean isAccountNonLocked() {
    return true;
  }


  
  public boolean isCredentialsNonExpired() {
    return true;
  }


  
  public boolean isEnabled() {
    return true;
  }


  
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    com.cgm.ejemploSpring.demoProyectoSpring.security.services.UserDetailsImpl user = (com.cgm.ejemploSpring.demoProyectoSpring.security.services.UserDetailsImpl)o;
    return Objects.equals(this.id, user.id);
  }
}
