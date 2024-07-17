package br.com.ecommerce.users.unit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import br.com.ecommerce.users.controller.UserController;
import br.com.ecommerce.users.model.dto.AddressDTO;
import br.com.ecommerce.users.model.dto.UserResponseDTO;
import br.com.ecommerce.users.model.dto.UserUpdateDTO;
import br.com.ecommerce.users.service.UserService;

@WebMvcTest(UserController.class)
@AutoConfigureJsonTesters
class UserControllerUnitTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private JacksonTester<UserUpdateDTO> userUpdateDTOJson;

	@MockBean
	private UserService service;


	@Test
	@DisplayName("Unit - updateUser - Should return status 200 and user data")
	void updateUserTest01() throws IOException, Exception {
		// arrange
		UserUpdateDTO requestBody = new UserUpdateDTO(
			"update name",
			"update@email.com",
			null, // can be null, but cannot have a value that is invalid
			new AddressDTO());
		
		UserResponseDTO serviceReturnMock = new UserResponseDTO(
			1L,
			requestBody.getName(),
			requestBody.getEmail(),
			"(11) 11111-1111",
			new AddressDTO()
		);
		when(service.updateUser(any(), any())).thenReturn(serviceReturnMock);

		// act and assert
		mvc.perform(
			put("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(userUpdateDTOJson.write(requestBody).getJson())
				.header("Authorization", "Bearer token")
		)
		// assert
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").exists())
		.andExpect(jsonPath("$.name").exists())
		.andExpect(jsonPath("$.email").exists())
		.andExpect(jsonPath("$.phone_number").exists())
		.andExpect(jsonPath("$.address").exists());

		verify(service).updateUser(any(), any());
	}
	@Test
	@DisplayName("Unit - updateUser - Should return status 400 when the phone number is invalid")
	void updateUserTest02() throws IOException, Exception {
		// arrange
		UserUpdateDTO requestBody = new UserUpdateDTO(
			null,
			null,
			"123",
			null
		);

		// act
		mvc.perform(
			put("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(userUpdateDTOJson.write(requestBody).getJson())
				.header("Authorization", "Bearer token")
		)
		// assert
		.andExpect(status().isBadRequest());

		verifyNoInteractions(service);
	}
	@Test
	@DisplayName("Unit - updateUser - Must return status 400 when the request does not have the Authorization header")
	void updateUserTest03() throws IOException, Exception {
		// arrange
		UserUpdateDTO requestBody = new UserUpdateDTO("update name", "update@email.com", null, new AddressDTO());
		
		UserResponseDTO serviceReturnMock = new UserResponseDTO(
			1L,
			"User-san",
			"user.san@email.com",
			"(11) 11111-1111",
			new AddressDTO()
		);
		when(service.updateUser(any(), any())).thenReturn(serviceReturnMock);

		// act and assert
		mvc.perform(
			put("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(userUpdateDTOJson.write(requestBody).getJson())
				// missing Authorization header
		)
		.andExpect(status().isBadRequest());

		verifyNoInteractions(service);
	}
}