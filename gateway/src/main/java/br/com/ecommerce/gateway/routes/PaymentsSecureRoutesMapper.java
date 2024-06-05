package br.com.ecommerce.gateway.routes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpMethod;

public class PaymentsSecureRoutesMapper implements SecureRoutesMapper {

	public Map<RequestInfo, List<String>> getRoutes() {
		Map<RequestInfo, List<String>> map = new HashMap<>();

		map.put(new RequestInfo("/payments", HttpMethod.GET), Arrays.asList("ADMIN"));
		map.put(new RequestInfo("/payments/1", HttpMethod.PATCH), Arrays.asList("ADMIN"));
		
		return map;
	}
}