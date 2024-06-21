package br.com.ecommerce.gateway.routes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpMethod;

public class AccountSecureRoutesImpl implements ISecureRoutes {

	public Map<RequestInfo, List<String>> getRoutes() {
		Map<RequestInfo, List<String>> map = new HashMap<>();

		map.put(new RequestInfo("/account/create/employee", HttpMethod.POST), Arrays.asList("EMPLOYEE", "ADMIN"));
		
		return map;
	}
}