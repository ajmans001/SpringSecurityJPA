package com.cgm.ejemploSpring.demoProyectoSpring.response;

import java.util.List;

import com.cgm.ejemploSpring.demoProyectoSpring.entities.CatRol;


public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private Integer id;
	private String username;
	private String email;
	private String nombre;
	private List<CatRol> perfiles;
	

	public JwtResponse(String accessToken, Integer id, String username, String email, List<CatRol> perfiles,
			String nombre) {
		this.token = accessToken;
		this.id = id;
		this.username = username;
		this.email = email;
		this.perfiles = perfiles;
		this.nombre = nombre;
	}

	public String getAccessToken() {
		return this.token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return this.type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<CatRol> getPerfiles() {
		return this.perfiles;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
}
