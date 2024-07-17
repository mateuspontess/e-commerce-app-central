package br.com.ecommerce.users.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import br.com.ecommerce.users.model.Address;
import br.com.ecommerce.users.model.User;
import br.com.ecommerce.users.model.UserRole;
import br.com.ecommerce.users.model.dto.AddressDTO;
import br.com.ecommerce.users.model.dto.UserIdAndRoleDTO;
import br.com.ecommerce.users.model.dto.UserUpdateDTO;
import br.com.ecommerce.users.repository.UserRepository;
import br.com.ecommerce.users.service.TokenService;
import br.com.ecommerce.users.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Transactional
@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.AUTO_CONFIGURED)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserServiceIntegrationTest {

    @Autowired
    private UserService service;
    @Autowired
    private UserRepository repository;
    @MockBean
    private TokenService tokenService;

    private final String VALID_USERNAME = "username-san";
    private User user = User.builder()
        .username(VALID_USERNAME)
        .password(new BCryptPasswordEncoder().encode("password-san"))
        .role(UserRole.ADMIN)
        .address(new Address())
        .cpf("157.750.300-79")
        .email("email@email.com")
        .name("name-san")
        .phone_number("(11) 11111-1111")
        .build();

    @BeforeEach
    void setup() {
        repository.save(this.user);
    }

    @Test
    @DisplayName("Integration - getUserByUsername - Should find the user and return data")
    void getUserByUsernameTest01() {
        // arrange
        String token = "bearer token";
        when(tokenService.validateToken(anyString())).thenReturn(VALID_USERNAME);

        // act
        var result = this.service.getUserByUsername(token);

        // assert
        assertTrue(() -> result instanceof UserIdAndRoleDTO);
        assertEquals(1, result.getId());
        assertEquals(UserRole.ADMIN, result.getRole());
    }
    @Test
    @DisplayName("Integration - getUserByUsername - Should not find the user")
    void getUserByUsernameTest02() {
        // arrange
        String token = "bearer token";
        when(tokenService.validateToken(anyString())).thenReturn("INVALID USERNAME");

        // act and assert
        assertThrows(EntityNotFoundException.class, () -> this.service.getUserByUsername(token));
    }

    @Test
    @DisplayName("Integration - updateUser - Must update the user")
    void updateUserTest01() {
        // arrange
        UserUpdateDTO inputUpdateData = new UserUpdateDTO(
            "New Name", 
            "new@email.com", 
            "(22) 22222-2222", 
            new AddressDTO());

        when(tokenService.validateToken(anyString())).thenReturn(VALID_USERNAME);

        // act
        var result = service.updateUser(inputUpdateData, "bearer token");

        // assert
        assertEquals(this.user.getName(), result.getName());
        assertEquals(this.user.getEmail(), result.getEmail());
        assertEquals(this.user.getPhone_number(), result.getPhone_number());
    }
}