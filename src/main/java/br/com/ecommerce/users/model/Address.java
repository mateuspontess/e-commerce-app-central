package br.com.ecommerce.users.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class Address {

	private String street;
	private String neighborhood;
	private String postal_code;
	private String number;
	private String complement;
	private String city;
	private String state;


	public void updateAddress(Address updatedData) {
		if (updatedData.getStreet() != null && !updatedData.getStreet().isBlank()) {
			this.street = updatedData.getStreet();
		}
		if (updatedData.getNeighborhood() != null && !updatedData.getNeighborhood().isBlank()) {
			this.neighborhood = updatedData.getNeighborhood();
		}
		if (updatedData.getPostal_code() != null && !updatedData.getPostal_code().isBlank()) {
			this.postal_code = updatedData.getPostal_code();
		}
		if (updatedData.getNumber() != null && !updatedData.getNumber().isBlank()) {
			this.number = updatedData.getNumber();
		}
		if (updatedData.getComplement() != null && !updatedData.getComplement().isBlank()) {
			this.complement = updatedData.getComplement();
		}
		if (updatedData.getCity() != null && !updatedData.getCity().isBlank()) {
			this.city = updatedData.getCity();
		}
		if (updatedData.getState() != null && !updatedData.getState().isBlank()) {
			this.state = updatedData.getState();
		}
	}
}