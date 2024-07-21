package com.poc.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poc.config.JwtProvider;
import com.poc.entity.Cart;
import com.poc.entity.User;
import com.poc.enums.User_Role;
import com.poc.repository.CartRepository;
import com.poc.repository.UserRepository;
import com.poc.request.LoginRequest;
import com.poc.response.AuthResponse;
import com.poc.service.CustomUserDetailsService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private CartRepository cartRepository;

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {

		User isEmailExist = userRepository.findByEmail(user.getEmail());
		if (isEmailExist != null) {
			throw new Exception("Email already used with other account");
		}

		User createduser = new User();
		createduser.setEmail(user.getEmail());
		createduser.setFullName(user.getFullName());
		createduser.setRole(user.getRole());
		createduser.setPassword(passwordEncoder.encode(user.getPassword()));
		User saveUser = userRepository.save(createduser);
		Cart cart = new Cart();
		cart.setCustomer(saveUser);
		cartRepository.save(cart);

		Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		;
		String jwt = jwtProvider.generateToken(authentication);
		AuthResponse authResponse = new AuthResponse();
		authResponse.setJwt(jwt);
		authResponse.setMessage("Registration success");
		authResponse.setRole(saveUser.getRole());

		return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
	}

	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest loginRequest) {

		String userName = loginRequest.getEmail();
		String password = loginRequest.getPassword();

		Authentication authentication = authentication(userName, password);
		Collection<? extends GrantedAuthority> authorities=authentication.getAuthorities();
		String role=authorities.isEmpty() ? null :authorities.iterator().next().getAuthority();
		
		
		String jwt = jwtProvider.generateToken(authentication);
		AuthResponse authResponse = new AuthResponse();
		authResponse.setJwt(jwt);
		authResponse.setMessage("Registration success");
		
		authResponse.setRole(User_Role.valueOf(role));

		return new ResponseEntity<>(authResponse, HttpStatus.OK);
		
	}

	private Authentication authentication(String username, String password) {

		UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
		if (userDetails == null) {
			throw new BadCredentialsException("Invalid userName");
		}
		if (!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("invalid password");
		}

		return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

	}
}
