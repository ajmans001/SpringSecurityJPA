package com.cgm.ejemploSpring.demoProyectoSpring.controllers;



import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.cgm.ejemploSpring.demoProyectoSpring.security.jwt.JwtUtils;
import com.cgm.ejemploSpring.demoProyectoSpring.security.services.UserDetailsImpl;
import com.cgm.ejemploSpring.demoProyectoSpring.entities.CatRol;
import com.cgm.ejemploSpring.demoProyectoSpring.entities.CatUsuario;
import com.cgm.ejemploSpring.demoProyectoSpring.repository.RoleRepository;
import com.cgm.ejemploSpring.demoProyectoSpring.repository.UserRepository;
import com.cgm.ejemploSpring.demoProyectoSpring.request.LoginRequest;
import com.cgm.ejemploSpring.demoProyectoSpring.request.SignupRequest;
import com.cgm.ejemploSpring.demoProyectoSpring.response.JwtResponse;
import com.cgm.ejemploSpring.demoProyectoSpring.response.MessageResponse;

@CrossOrigin(origins = { "*" }, maxAge = 3600L)
@RestController
@RequestMapping({ "/api/back/security" })
public class AuthController {
	private static final Logger logger = 
			Logger.getLogger("AuthController");

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@GetMapping({"/obtener"})
	public ResponseEntity<?> getUsuarioById(@RequestParam(name = "id") int id) {
		System.out.println("Consultando REST usuario");
		return ResponseEntity.ok(userRepository.findById_usuario(id));
	}

	@PostMapping({ "/login" })
	public ResponseEntity<?> authenticateUser(
			@Valid @RequestBody LoginRequest 
			loginRequest) {
		Authentication authentication = 
				this.authenticationManager
				.authenticate((Authentication) 
new UsernamePasswordAuthenticationToken(
		loginRequest.getUsername(),
						loginRequest.getPassword()));

		SecurityContextHolder.getContext()
		.setAuthentication(authentication);
		String jwt = this.jwtUtils
				.generateJwtToken(authentication);

		UserDetailsImpl userDetails = 
				(UserDetailsImpl) authentication
				.getPrincipal();

		List<String> roles = (List<String>) 
				userDetails.getAuthorities()
				.stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		List<CatRol> rolesEntity = this.roleRepository
				.findInNombre(roles);

		

		return ResponseEntity.ok(new JwtResponse(jwt, 
				userDetails.getId(), 
				userDetails.getUsername(),
					userDetails.getEmail(), 
					rolesEntity, 
					userDetails.getNombre()));
		
	}

	@GetMapping({ "/tokenAlive" })
	@PreAuthorize("isAuthenticated() and "
			+ "@securityService.hasPermission('security')")
	public ResponseEntity<?> isTokenAlive(
			@RequestParam(name = "token") String token) {
		return ResponseEntity.ok(new MessageResponse(
				"{ \"token\":\"" + token + 
				"\",\"status\":\"" + 
						this.jwtUtils.validateJwtToken(
								token) + "\"}", 200));
	}

	@GetMapping({ "/tokenAliveDate" })
	public ResponseEntity<?> isTokenAliveDate(
			@RequestParam(name = "token") String token) {
		return ResponseEntity.ok(new MessageResponse(
				"{ \"token\":\"" + token + 
				"\",\"expires\":\"" + 
						this.jwtUtils
						.expireJWTToken(token) + "\"}", 
						200));
	}
	

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(
			@Valid @RequestBody 
			SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(
				signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse(
			"Error: Username is already taken!", 201));
		}

		if (userRepository.existsByCorreo(signUpRequest
				.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse(
			"Error: Email is already in use!", 201));
		}

		// Create new user's account
		CatUsuario user = new CatUsuario();
		user.setUsername(signUpRequest.getUsername());
		user.setCorreo(signUpRequest.getEmail());
		user.setPassword(encoder.encode(
				signUpRequest.getPassword()));
		user.setTelefono(signUpRequest.getTelefono());
		user.setNombre(signUpRequest.getNombre());
		user.setAp_paterno(signUpRequest.getApPaterno());

		Set<String> strRoles = signUpRequest.getRole();
		Set<CatRol> roles = new HashSet<>();

		if (strRoles == null) {
			CatRol userRole = roleRepository.
					findByNombre("ROLE_USER")
					.orElseThrow(() -> new 
							RuntimeException(
				"Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					CatRol adminRole = roleRepository.
					findByNombre("ROLE_ADMIN")
							.orElseThrow(() -> new 
		RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "mod":
					CatRol modRole = roleRepository.
					findByNombre("ROLE_MOD")
							.orElseThrow(() -> new 
		RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					CatRol userRole = roleRepository.
					findByNombre("ROLE_USER")
							.orElseThrow(() -> new 
		RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(
new MessageResponse("User registered successfully!", 200));
	}
}
