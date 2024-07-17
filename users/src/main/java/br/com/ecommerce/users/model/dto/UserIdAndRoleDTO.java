package br.com.ecommerce.users.model.dto;

import br.com.ecommerce.users.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserIdAndRoleDTO {

	private Long id;
	private UserRole role;
}