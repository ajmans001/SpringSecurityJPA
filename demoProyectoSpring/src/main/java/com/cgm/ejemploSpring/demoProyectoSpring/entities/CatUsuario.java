package com.cgm.ejemploSpring.demoProyectoSpring.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "cat_usuarios")
public class CatUsuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_usuario;
	@NotBlank
	@Size(max = 100)
	private String nombre;
	@NotBlank
	@Size(max = 50)
	private String ap_paterno;
	@Size(max = 50)
	private String ap_materno;
	@NotBlank
	@Size(max = 15)
	private String telefono;
	@NotBlank
	@Size(max = 30)
	private String username;
	@NotBlank
	@Size(max = 100)
	@Email
	private String correo;
	@JsonIgnore
	@NotBlank
	@Size(max = 120)
	private String password;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "usuarios_roles", joinColumns = { @JoinColumn(name = "id_usuario") }, inverseJoinColumns = {
			@JoinColumn(name = "id_rol") })
	 private Set<CatRol> roles = new HashSet<>();

	

	public CatUsuario() {
	}

	public CatUsuario(Integer idUsuario) {
		 this.id_usuario = idUsuario;
	}

	public Integer getId_usuario() {
		return this.id_usuario;
	}

	public void setId_usuario(Integer id_usuario) {
		this.id_usuario = id_usuario;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCorreo() {
		return this.correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<CatRol> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<CatRol> roles) {
		this.roles = roles;
	}

	public String getAp_paterno() {
		return this.ap_paterno;
	}

	public void setAp_paterno(String ap_paterno) {
		this.ap_paterno = ap_paterno;
	}

	public String getAp_materno() {
		return this.ap_materno;
	}

	public void setAp_materno(String ap_materno) {
		this.ap_materno = ap_materno;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	
}
