package br.com.ecommerce.gateway.routes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpMethod;

public class OrdersSecureRoutesMapper implements SecureRoutesMapper {

	public Map<RequestInfo, List<String>> getRoutes() {
		Map<RequestInfo, List<String>> map = new HashMap<>();

		map.put(new RequestInfo("/orders", HttpMethod.POST), Arrays.asList("CLIENT", "ADMIN"));
		map.put(new RequestInfo("/orders", HttpMethod.GET), Arrays.asList("CLIENT", "ADMIN"));
		map.put(new RequestInfo("/orders/1", HttpMethod.GET), Arrays.asList("CLIENT", "ADMIN"));
		map.put(new RequestInfo("/orders/1", HttpMethod.PATCH), Arrays.asList("CLIENT", "ADMIN"));
		
		return map;
	}
}