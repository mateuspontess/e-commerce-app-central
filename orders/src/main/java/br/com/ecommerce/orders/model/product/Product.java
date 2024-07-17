package br.com.ecommerce.orders.model.product;

import java.math.BigDecimal;

import br.com.ecommerce.orders.model.order.Order;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Entity(name = "Product")
@Table(name = "products")
public class Product {

	private static final BigDecimal MINIMAL_PRICE = BigDecimal.ZERO;
	private static final int MINIMAL_UNITS = 1;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Setter
	@ManyToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name = "order_id") 
	private Order order;

	private Long productId;
	private Integer unit;
	private BigDecimal price;

	public Product(Long productId, BigDecimal price, Integer unit) {
		checkNotNull(productId, "productId");
		checkNotNull(price, "price");
		checkNotNull(unit, "unit");

		int result = price.compareTo(MINIMAL_PRICE);
		if (result <= 0)
			throw new IllegalArgumentException("Price cannot be lower than: " + MINIMAL_PRICE.toString());

		if (unit < MINIMAL_UNITS)
			throw new IllegalArgumentException("Units cannot be lower than: " + MINIMAL_UNITS);

		this.productId = productId;
		this.unit = unit;
		this.price = price;
	}

	private void checkNotNull(Object field, String fieldName) {
		if (field == null)
			throw new IllegalArgumentException("Cannot be null: " + fieldName);
	}
}