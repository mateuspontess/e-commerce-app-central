package br.com.ecommerce.products.infra.entity.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter @Setter
@Entity(name = "ProductSpec")
@Table(name = "product_specs")
public class ProductSpec {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String attribute;

	@Column(name = "\"value\"")
	private String value;

	@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "product_id")
	private Product product;


	public ProductSpec(String attribute, String value) {
		this.attribute = attribute;
		this.value = value;
	}

	@Override
	public String toString() {
		return "ProductSpec(id=" + this.id + ", " + this.attribute + ", " + this.value + ")";
	}
}