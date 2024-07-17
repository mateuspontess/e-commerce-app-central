package br.com.ecommerce.products.infra.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ecommerce.products.infra.entity.manufacturer.Manufacturer;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long>{

	Optional<Manufacturer> findByName(String manufacturerName);
}