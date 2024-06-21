package br.com.ecommerce.gateway.routes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpMethod;

public class ProductsSecureRoutesImpl implements ISecureRoutes {

	public Map<RequestInfo, List<String>> getRoutes() {
		Map<RequestInfo, List<String>> map = new HashMap<>();

		map.put(new RequestInfo("/manufacturers", HttpMethod.POST), Arrays.asList("EMPLOYEE", "ADMIN"));
		map.put(new RequestInfo("/manufacturers/1", HttpMethod.PUT), Arrays.asList("EMPLOYEE", "ADMIN"));
		
		map.put(new RequestInfo("/products", HttpMethod.POST), Arrays.asList("EMPLOYEE", "ADMIN"));
		map.put(new RequestInfo("/products/1", HttpMethod.PUT), Arrays.asList("EMPLOYEE", "ADMIN"));
		map.put(new RequestInfo("/products/1/stocks", HttpMethod.PUT), Arrays.asList("EMPLOYEE", "ADMIN"));
		
		return map;
	}
}