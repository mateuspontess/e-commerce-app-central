package br.com.ecommerce.gateway.routes;

import java.util.List;
import java.util.Map;

public interface ISecureRoutes {
	Map<RequestInfo, List<String>> getRoutes();
}