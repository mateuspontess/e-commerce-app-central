package br.com.ecommerce.users.model.dto;

import br.com.ecommerce.users.model.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    private String street;
    private String neighborhood;
    private String postal_code;
    private String number;
    private String complement;
    private String city;
    private String state;

    public AddressDTO(Address address) {
        this.street = address.getStreet();
        this.neighborhood = address.getNeighborhood();
        this.postal_code = address.getPostal_code();
        this.number = address.getNumber();
        this.complement = address.getComplement();
        this.city = address.getCity();
        this.state = address.getState();
    }
}