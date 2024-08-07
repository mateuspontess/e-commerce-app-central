package br.com.ecommerce.products.unit;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import br.com.ecommerce.products.api.dto.manufacturer.ManufacturerDTO;
import br.com.ecommerce.products.api.dto.manufacturer.ManufacturerResponseDTO;
import br.com.ecommerce.products.business.service.ManufacturerService;
import br.com.ecommerce.products.infra.entity.manufacturer.Manufacturer;
import br.com.ecommerce.products.infra.repository.ManufacturerRepository;
import br.com.ecommerce.products.utils.RandomUtils;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class ManufacturerServiceUnitTest {

    @Mock
    private ManufacturerRepository repository;
    @InjectMocks
    private ManufacturerService service;

    
    @Test
    @DisplayName("Unit - findAllManufacturers - Must return all Manufacturers")
    void findAllManufacturersTest01() {
        // arrange
        Manufacturer manufacturer = RandomUtils.getRandomManufacturer();
        when(repository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(manufacturer)));
        
        // act
        ManufacturerResponseDTO result = service.findAllManufacturers(PageRequest.of(0, 10))
            .getContent()
            .get(0);

        // assert
        assertNotNull(result);
        assertEquals(manufacturer.getName(), result.getName());
    }

    @Test
    @DisplayName("Unit - findManufacturerById - Must return manufacturer by ID")
    void findManufacturerByIdTest01() {
        // arrange
        Manufacturer manufacturer = RandomUtils.getRandomManufacturer();
        when(repository.findById(any())).thenReturn(Optional.of(manufacturer));
        
        // act
        ManufacturerResponseDTO result = service.findManufacturerById(1L);

        // assert
        assertNotNull(result);
        assertEquals(manufacturer.getName(), result.getName());
    }
    @Test
    @DisplayName("Unit - findManufacturerById - Should throw exception when not finding manufacturer")
    void findManufacturerByIdTest02() {
        assertThrows(EntityNotFoundException.class, () -> service.findManufacturerById(1L));
    }

    @Test
    @DisplayName("Unit - saveManufacturer - Must save manufacturer")
    void saveManufacturerTest01() {
        // arrange
        ManufacturerDTO requestBody = new ManufacturerDTO("AMD");
        when(repository.save(any())).thenReturn(new Manufacturer(requestBody.getName()));
        
        // act
        ManufacturerResponseDTO result = service.saveManufacturer(requestBody);

        // assert
        assertEquals(requestBody.getName(), result.getName());
    }

    @Test
    @DisplayName("Unit - updateManufacturerData - Must update manufacturer")
    void updateManufacturerDataTest01() {
        // arrange
        Manufacturer target = RandomUtils.getRandomManufacturer();
        ManufacturerDTO requestBody = new ManufacturerDTO("INTEL");
        when(repository.findById(any())).thenReturn(Optional.of(target));
        
        // act
        service.updateManufacturerData(1L, requestBody);

        assertEquals(requestBody.getName(), target.getName());
    }
    @Test
    @DisplayName("Unit - updateManufacturerData - Should throw exception when not finding manufacturer")
    void updateManufacturerDataTest02() {
        // arrange
        assertThrows(EntityNotFoundException.class, 
        () -> service.updateManufacturerData(1L, new ManufacturerDTO("AMD")));
    }
}