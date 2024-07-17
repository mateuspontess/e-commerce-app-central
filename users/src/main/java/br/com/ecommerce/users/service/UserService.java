package br.com.ecommerce.users.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ecommerce.users.model.Address;
import br.com.ecommerce.users.model.User;
import br.com.ecommerce.users.model.dto.UserIdAndRoleDTO;
import br.com.ecommerce.users.model.dto.UserResponseDTO;
import br.com.ecommerce.users.model.dto.UserUpdateDTO;
import br.com.ecommerce.users.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TokenService tokenService;


	public UserIdAndRoleDTO getUserByUsername(String token) {
		User user = this.getUserByToken(token);
		return new UserIdAndRoleDTO(user.getId(), user.getRole());
	}

	public UserResponseDTO updateUser(UserUpdateDTO dto, String token) {
		User user = this.getUserByToken(token);

		Address addressUpdate = new Address();
		BeanUtils.copyProperties(dto.getAddress(), addressUpdate);
		user.update(dto.getName(), dto.getEmail(), dto.getPhone_number(), addressUpdate);

		return new UserResponseDTO(user);
	}

	private User getUserByToken(String token) {
		String username = tokenService.validateToken(token);
		return userRepository
			.findByUsername(username)
			.orElseThrow(EntityNotFoundException::new);
	}
}