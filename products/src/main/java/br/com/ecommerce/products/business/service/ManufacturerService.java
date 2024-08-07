package br.com.ecommerce.products.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.ecommerce.products.api.dto.manufacturer.ManufacturerDTO;
import br.com.ecommerce.products.api.dto.manufacturer.ManufacturerResponseDTO;
import br.com.ecommerce.products.infra.entity.manufacturer.Manufacturer;
import br.com.ecommerce.products.infra.repository.ManufacturerRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ManufacturerService {

	@Autowired
	private ManufacturerRepository repository;


	public Page<ManufacturerResponseDTO> findAllManufacturers(Pageable pageable){
		return repository
			.findAll(pageable)
			.map(ManufacturerResponseDTO::new);
	}

	public ManufacturerResponseDTO findManufacturerById(Long id){
		Manufacturer mf = repository.findById(id)
			.orElseThrow(EntityNotFoundException::new);
		return new ManufacturerResponseDTO(mf);
	}

	public ManufacturerResponseDTO saveManufacturer(ManufacturerDTO dto){
		return new ManufacturerResponseDTO(repository.save(new Manufacturer(dto.getName())));
	}

	public ManufacturerResponseDTO updateManufacturerData(Long id, ManufacturerDTO dto){
		Manufacturer mf = repository.findById(id).orElseThrow(EntityNotFoundException::new);
		mf.updateName(dto.getName());
		return new ManufacturerResponseDTO(mf);
	}
}