package br.com.ecommerce.users.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.ecommerce.users.model.Address;

class AddressUnitTest {

	@Test
	@DisplayName("Unit - updateAddress - address must be updated")
	void updateAddress01() {
		// arrange
		Address target = this.getAddress(); 

		Address updateData = Address.builder()
			.street("street")
			.neighborhood("neighborhood")
			.postal_code("postal_code")
			.number("number")
			.complement("complement")
			.city("city")
			.state("state")
			.build();

		// act
		target.updateAddress(updateData);

		// assert
		assertEquals(updateData.getStreet(), target.getStreet());
		assertEquals(updateData.getNeighborhood(), target.getNeighborhood());
		assertEquals(updateData.getPostal_code(), target.getPostal_code());
		assertEquals(updateData.getNumber(), target.getNumber());
		assertEquals(updateData.getComplement(), target.getComplement());
		assertEquals(updateData.getCity(), target.getCity());
		assertEquals(updateData.getState(), target.getState());
	}

	@Test
	@DisplayName("Unit - updateAddress - address should not be updated when entries are null")
	void updateAddress02() {
		// arrange
		Address target = this.getAddress(); 
		Address updateData = new Address(); // null attributes

		// act
		target.updateAddress(updateData);

		// assert
		assertNotEquals(updateData.getStreet(), target.getStreet());
		assertNotEquals(updateData.getNeighborhood(), target.getNeighborhood());
		assertNotEquals(updateData.getPostal_code(), target.getPostal_code());
		assertNotEquals(updateData.getNumber(), target.getNumber());
		assertNotEquals(updateData.getComplement(), target.getComplement());
		assertNotEquals(updateData.getCity(), target.getCity());
		assertNotEquals(updateData.getState(), target.getState());
	}

	@Test
	@DisplayName("Unit - updateAddress - address should not be updated when entries are blank")
	void updateAddress03() {
		// arrange
		Address target = this.getAddress();

		Address updateData = Address.builder()
			.street("")
			.neighborhood("")
			.postal_code("")
			.number("")
			.complement("")
			.city("")
			.state("")
			.build();

		// act
		target.updateAddress(updateData);

		// assert
		assertNotEquals(updateData.getStreet(), target.getStreet());
		assertNotEquals(updateData.getNeighborhood(), target.getNeighborhood());
		assertNotEquals(updateData.getPostal_code(), target.getPostal_code());
		assertNotEquals(updateData.getNumber(), target.getNumber());
		assertNotEquals(updateData.getComplement(), target.getComplement());
		assertNotEquals(updateData.getCity(), target.getCity());
		assertNotEquals(updateData.getState(), target.getState());
	}

	private Address getAddress() {
		return Address.builder()
			.street("street")
			.neighborhood("neighborhood")
			.postal_code("postal_code")
			.number("number")
			.complement("complement")
			.city("city")
			.state("state")
			.build();
	}
}