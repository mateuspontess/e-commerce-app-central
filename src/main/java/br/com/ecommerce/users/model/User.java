package br.com.ecommerce.users.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "User")
@Table(name = "users")
public class User {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String username;

	private String password;
	private String name;

	@Column(unique = true)
	private String email;

	@Column(unique = true)
	private String phone_number;

	@Column(unique = true)
	private String cpf;

	@Embedded
	private Address address;

	@Enumerated(EnumType.STRING)
	private UserRole role;


	public void update(String name, String email, String phoneNumber, Address address) {
		if (name != null && !name.isBlank()) {
			this.name = name;
		}
		if (email != null && !email.isBlank()) {
			this.email = email;
		}
		if (phoneNumber != null && !phoneNumber.isBlank()) {
			this.phone_number = phoneNumber;
		}

		if (address != null) {
			if (this.address == null) {
				Address update = new Address();
				update.updateAddress(update);
				this.address = update;
			}
			this.address.updateAddress(address);
		}
	}
}