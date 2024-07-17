package br.com.ecommerce.products.unit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.ecommerce.products.infra.entity.manufacturer.Manufacturer;

public class ManufacturerUnitTest {

    private final String BLANK = "";
    private final String NULL = null;


    @Test
    @DisplayName("Unit - Creating manufacturer with valid and invalid names")
    void createManufacturerTest01() {
        assertThrows(IllegalArgumentException.class, () -> new Manufacturer(NULL));
        assertThrows(IllegalArgumentException.class, () -> new Manufacturer(BLANK));
        
        assertDoesNotThrow(() -> new Manufacturer("AMD"));
    }

    @Test
    @DisplayName("Unit - Updating manufacturer name with valid and invalid names")
    void updateName() {
        Manufacturer manufacturer = new Manufacturer("AMD");
        assertThrows(IllegalArgumentException.class, () -> manufacturer.updateName(NULL));
        assertThrows(IllegalArgumentException.class, () -> manufacturer.updateName(BLANK));
        
        assertDoesNotThrow(() -> manufacturer.updateName("INTEL"));
    }
}