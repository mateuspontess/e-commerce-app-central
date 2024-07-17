package br.com.ecommerce.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ecommerce.users.model.dto.UserIdAndRoleDTO;
import br.com.ecommerce.users.model.dto.UserResponseDTO;
import br.com.ecommerce.users.model.dto.UserUpdateDTO;
import br.com.ecommerce.users.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService service;

	@GetMapping
	public ResponseEntity<UserIdAndRoleDTO> getUserIdAndRoleByToken(@RequestHeader("Authorization") String token) {
		return ResponseEntity.ok(service.getUserByUsername(token));
	}

	@PutMapping
	@Transactional
	public ResponseEntity<UserResponseDTO> updateUser(
		@RequestBody @Valid UserUpdateDTO dto,
		@RequestHeader("Authorization") String token
	) {
		return ResponseEntity.ok().body(service.updateUser(dto, token));
	}
}