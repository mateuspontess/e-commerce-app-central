package br.com.ecommerce.users.integration;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import br.com.ecommerce.users.model.Address;
import br.com.ecommerce.users.model.User;
import br.com.ecommerce.users.model.UserRole;
import br.com.ecommerce.users.model.dto.AddressDTO;
import br.com.ecommerce.users.model.dto.UserUpdateDTO;
import br.com.ecommerce.users.repository.UserRepository;
import br.com.ecommerce.users.service.TokenService;
import jakarta.transaction.Transactional;

@Transactional
@SpringBootTest
@AutoConfigureWebMvc
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@AutoConfigureTestDatabase(replace = Replace.AUTO_CONFIGURED)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserRepository repository;
    @Autowired
    private JacksonTester<UserUpdateDTO> userUpdateDTOJson;

    @MockBean
    private TokenService tokenService;

    private final String VALID_USERNAME = "username-san";
    private User user = User.builder()
        .username(VALID_USERNAME)
        .password(new BCryptPasswordEncoder().encode("password-san"))
        .role(UserRole.ADMIN)
        .cpf("157.750.300-79")
        .email("email@email.com")
        .name("name-san")
        .phone_number("(11) 99999-9999")
        .address(new Address(
            "street",
            "neighborhood",
            "postal_code",
            "number",
            "complement",
            "city",
            "state"
        ))
        .build();

    @BeforeEach
    void setup() {
        repository.save(this.user);
    }

    @Test
    @DisplayName("Integration - getUserIdAndRoleByToken - Must return status 200 and user data")
    void getUserIdAndRoleByTokenTest01() throws Exception {
        // arrange
        String token = "bearer token";
        when(tokenService.validateToken(anyString())).thenReturn(VALID_USERNAME);

        // act
        mvc.perform(
            get("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
        )
        // assert
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.role").value(UserRole.ADMIN.toString()));
    }

    @Test
    @DisplayName("Integration - updateUser - Must return status 200 and user data updated")
    void updateUserTest01() throws Exception {
        // arrange
        UserUpdateDTO requestBody = new UserUpdateDTO(
            "New Name", 
            "new@email.com", 
            "(47) 99999-9999", 
            new AddressDTO());
        String token = "bearer token";
        when(tokenService.validateToken(anyString())).thenReturn(VALID_USERNAME);

        // act
        mvc.perform(
            put("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userUpdateDTOJson.write(requestBody).getJson())
                .header("Authorization", token)
        )
        // assert
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.name").value(requestBody.getName()))
        .andExpect(jsonPath("$.email").value(requestBody.getEmail()))
        .andExpect(jsonPath("$.phone_number").value(requestBody.getPhone_number()));
    }
}