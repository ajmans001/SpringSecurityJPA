package com.cgm.ejemploSpring.demoProyectoSpring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cgm.ejemploSpring.demoProyectoSpring.entities.CatUsuario;
import com.cgm.ejemploSpring.demoProyectoSpring.repository.UserRepository;



@CrossOrigin(origins = {"*"}, maxAge = 3600L)
@RestController
@RequestMapping({"/api/back/usuario"})
public class UsuarioAPi {
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping({"/obtener"})
	public ResponseEntity<?> getUsuarioById(@RequestParam(name = "id") int id) {
		return ResponseEntity.ok(userRepository.findById_usuario(id));
	}

}
