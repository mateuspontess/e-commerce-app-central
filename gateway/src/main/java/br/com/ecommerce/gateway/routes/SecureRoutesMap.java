package br.com.ecommerce.gateway.routes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SecureRoutesMap {

    private static final Map<String, Map<RequestInfo, List<String>>> servicesMap = new HashMap<>();

    static {
    	servicesMap.put("account", new AccountSecureRoutesMapper().getRoutes());
    	servicesMap.put("orders", new OrdersSecureRoutesMapper().getRoutes());
    	servicesMap.put("payments", new PaymentsSecureRoutesMapper().getRoutes());
    	servicesMap.put("products", new ProductsSecureRoutesMapper().getRoutes());
    }

    public static List<String> getRolesForRoute(String serviceName, RequestInfo routeKey) {
    	Map<RequestInfo, List<String>> routes = servicesMap.get(serviceName);
    	List<String> roles = routes != null ? routes.get(routeKey) : null;
    	
    	if (roles == null || roles.isEmpty()) {
    		return null;
    	}
    	return roles;
    }
}