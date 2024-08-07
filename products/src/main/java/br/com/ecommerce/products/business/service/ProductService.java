package br.com.ecommerce.products.business.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.ecommerce.products.api.dto.product.ProductDTO;
import br.com.ecommerce.products.api.dto.product.ProductIdAndUnitsDTO;
import br.com.ecommerce.products.api.dto.product.ProductResponseDTO;
import br.com.ecommerce.products.api.dto.product.ProductUpdateDTO;
import br.com.ecommerce.products.api.dto.product.ProductUpdateResponseDTO;
import br.com.ecommerce.products.api.dto.product.StockDTO;
import br.com.ecommerce.products.api.dto.product.StockResponseDTO;
import br.com.ecommerce.products.api.dto.product.StockWriteOffDTO;
import br.com.ecommerce.products.infra.entity.manufacturer.Manufacturer;
import br.com.ecommerce.products.infra.entity.product.Category;
import br.com.ecommerce.products.infra.entity.product.Product;
import br.com.ecommerce.products.infra.entity.product.ProductSpec;
import br.com.ecommerce.products.infra.entity.product.Stock;
import br.com.ecommerce.products.infra.repository.ManufacturerRepository;
import br.com.ecommerce.products.infra.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ManufacturerRepository manufacturerRepository;
	@Autowired
	private ModelMapper mapper;


	public ProductResponseDTO getProduct(Long id) {
		Product product = productRepository.findById(id)
			.orElseThrow(EntityNotFoundException::new);
		product.getSpecs();

		return mapper.map(product, ProductResponseDTO.class);
	}

	public Page<ProductResponseDTO> getAllProductWithParams(
			Pageable pageable, 
			String name, 
			Category category, 
			BigDecimal minPrice, 
			BigDecimal maxPrice,
			String manufacturer) {
		
		return productRepository
			.findAllByParams(pageable, name, category, minPrice, maxPrice, manufacturer)
			.map(ProductResponseDTO::new);
	}

	public Page<ProductResponseDTO> getAllBySpecs(Pageable pageable, List<Map<String, String>> map) {
		return productRepository
			.findProductsBySpecs(pageable, map)
			.map(ProductResponseDTO::new);
	}

	public List<Product> verifyProductsStocks(List<ProductIdAndUnitsDTO> productsRequest) {
		Map<Long, Integer> unitiesRequested = productsRequest.stream()
			.collect(Collectors.toMap(p -> p.getId(), p -> p.getUnit()));
		
		return this.getAllProductsByListOfIds(productsRequest.stream()
			.map(ProductIdAndUnitsDTO::getId)
			.toList()
			)
			.stream()
				.filter(p -> p != null && p.getStock().getUnit() < unitiesRequested.get(p.getId())) 
				.collect(Collectors.toList());
	}

	public List<Product> getAllProductsByListOfIds(List<Long> productsIds) {
		return productRepository.findAllById(productsIds);
	}

	public ProductUpdateResponseDTO updateProductData(Long id, ProductUpdateDTO dto) {
		Product currentProduct = productRepository.getReferenceById(id);
		Product updateData = mapper.map(dto, Product.class);
		
		if (updateData.getManufacturer() != null) {
			Manufacturer currentManufacturer = manufacturerRepository
				.findByName(currentProduct.getManufacturer().getName())
				.orElseThrow(EntityNotFoundException::new);
			currentManufacturer.removeProduct(currentProduct);
			
			Manufacturer newManufacturer = manufacturerRepository
				.findByName(updateData.getManufacturer().getName())
				.orElseThrow(() -> new EntityNotFoundException("Manufacturer not found. Create a Manufacturer to link it to a product"));
			updateData.setManufacturer(newManufacturer);
			newManufacturer.addProduct(currentProduct);

			currentProduct.update(updateData);
			manufacturerRepository.save(newManufacturer);
			return new ProductUpdateResponseDTO(currentProduct);
		}
		currentProduct.update(updateData);
		return new ProductUpdateResponseDTO(currentProduct);
	}

	public StockResponseDTO updateStockByProductId(Long productId, StockDTO dto) {
		Product target = productRepository.getReferenceById(productId);
		Stock stockUpdate = mapper.map(dto, Stock.class);
		
		target.updateStock(stockUpdate.getUnit());
		return new StockResponseDTO(target.getId(), target.getName(), target.getStock().getUnit());
	}
	public void updateStocks(List<StockWriteOffDTO> dto) {
		Map<Long, Integer> writeOffValueMap = dto.stream()
			.collect(Collectors.toMap(StockWriteOffDTO::getProductId, StockWriteOffDTO::getUnit));
		
		productRepository.findAllById(dto.stream()
			.map(StockWriteOffDTO::getProductId)
			.toList()
			)
			.forEach(p -> p.updateStock(writeOffValueMap.get(p.getId())));
	}


	public ProductResponseDTO createProduct(ProductDTO dto) {
		Product product = mapper.map(dto, Product.class);
		
		this.setManufacturer(product);
		this.createSpec(product);
		productRepository.save(product);
		
		return new ProductResponseDTO(product);
	}

	private void setManufacturer(Product product) {
		Manufacturer mf = manufacturerRepository
			.findByName(product.getManufacturer().getName().toUpperCase())
			.orElseThrow(EntityNotFoundException::new);

		product.setManufacturer(mf);
	}
	private void createSpec(Product product) {
		List<ProductSpec> specs = product.getSpecs();
		specs.forEach(spec -> spec.setProduct(product));
	}
}