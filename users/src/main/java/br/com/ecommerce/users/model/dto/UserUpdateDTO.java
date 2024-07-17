package br.com.ecommerce.users.model.dto;

import br.com.ecommerce.users.annotation.PhoneNumberValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {

    private String name;
    private String email;

    @PhoneNumberValidator
    private String phone_number;
    private AddressDTO address;
}