package com.cgm.ejemploSpring.demoProyectoSpring.security.services;

import com.cgm.ejemploSpring.demoProyectoSpring.entities.CatUsuario;
import com.cgm.ejemploSpring.demoProyectoSpring.repository.UserRepository;
import com.cgm.ejemploSpring.demoProyectoSpring.security.services.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserRepository userRepository;

	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		CatUsuario user = (CatUsuario) this.userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Not Found: " + username));

		return (UserDetails) UserDetailsImpl.build(user);
	}
}
