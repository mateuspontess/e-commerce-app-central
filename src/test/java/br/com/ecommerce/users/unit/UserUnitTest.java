package br.com.ecommerce.users.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.ecommerce.users.model.User;

class UserUnitTest {

	private User getUser() {
		return User.builder()
			.name("default")
			.email("default@email.com")
			.phone_number("1111111111111")
			.build();
	}

	@Test
	@DisplayName("Unit - update - User must be updated")
	void updateTest01() {
		// arrange
		User target = this.getUser();
		User updateData = User.builder()
			.name("Name updated")
			.email("update@email.com")
			.phone_number("2222222222222")
			.build();

		// act
		target.update(updateData.getName(), updateData.getEmail(), updateData.getPhone_number(), null);
		
		// assert
		assertEquals(updateData.getName(), target.getName());
		assertEquals(updateData.getEmail(), target.getEmail());
		assertEquals(updateData.getPhone_number(), target.getPhone_number());
	}

	@Test
	@DisplayName("Unit - update - User shoud not be updated when entries are null")
	void updateTest02() {
		// arrange
		User target = this.getUser();
		User updateData = new User(); // null attributes

		// act
		target.update(updateData.getName(), updateData.getEmail(), updateData.getPhone_number(), null);

		// assert
		assertNotEquals(updateData.getName(), target.getName());
		assertNotEquals(updateData.getEmail(), target.getEmail());
		assertNotEquals(updateData.getPhone_number(), target.getPhone_number());
	}

	@Test
	@DisplayName("Unit - update - User shoud not be updated when entries are null")
	void updateTest03() {
		// arrange
		User target = this.getUser();
			
		User updateData = User.builder()
			.name("")
			.email("")
			.phone_number("")
			.build();
		
		// act
		target.update(updateData.getName(), updateData.getEmail(), updateData.getPhone_number(), null);
		
		// assert
		assertNotEquals(updateData.getName(), target.getName());
		assertNotEquals(updateData.getEmail(), target.getEmail());
		assertNotEquals(updateData.getPhone_number(), target.getPhone_number());
	}
}