package br.com.ecommerce.gateway.filter;

import java.util.List;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;

import br.com.ecommerce.gateway.routes.RequestInfo;
import br.com.ecommerce.gateway.routes.SecureRoutesMap;
import reactor.core.publisher.Mono;

/**
 * A global filter responsible for security checks on incoming requests.
 */
@Component
public class SecurityFilter implements GlobalFilter {

    /**
     * Filters the incoming request based on security rules.
     *
     * @param exchange The current server web exchange.
     * @param chain    The filter chain to proceed with after security checks.
     * 
     * @return A Mono representing the completion of the filter operation.
     */
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
			ServerHttpRequest request = exchange.getRequest();
			
			String path = request.getPath().value();
			String serviceName = this.getServiceName(path);
			RequestInfo requestInfo = new RequestInfo(path, request.getMethod());
			List<String> authorizedRoles = SecureRoutesMap.getRolesForRoute(serviceName, requestInfo);
			
			if (authorizedRoles != null) {
				String token = request.getHeaders().getFirst("Authorization");
				if(token == null | token.isBlank())
					throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing authorization header");
				
				
				try {
					return exchange.getApplicationContext().getBean(WebClient.Builder.class)
							.build()
							.get()
							.uri("http://localhost:9095/users")
							.header("Authorization", token)
							.retrieve()
							.bodyToMono(UserDTO.class)
							.doOnNext(user -> this.verifyRole(authorizedRoles, user.role()))
							.flatMap(user -> {
								ServerHttpRequest modifiedRequest = exchange.getRequest()
										.mutate()
										.headers(httpHeaders -> httpHeaders.add("X-auth-user-id", String.valueOf(user.id())))
										.build();
								ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();
								return chain.filter(modifiedExchange);
							});
					
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
		    }
			return chain.filter(exchange);
	}

    /**
     * Extracts the service name from the given request path.
     *
     * @param path The request path.
     * @return The extracted service name.
     */
	private String getServiceName(String path) {
		int secondBarIndex = path.indexOf("/", path.indexOf("/")+1);
		
		if(secondBarIndex < 0) 
			return path.substring(1);
		return path.substring(1, secondBarIndex);
	}
	
    /**
     * Verifies if the user has the required role to access the route.
     *
     * @param rolesAllowOnTheRoute The roles allowed on the route.
     * @param userRole             The role of the user.
     * 
     * @throws ResponseStatusException if the user role is not authorized.
     */
	private void verifyRole(List<String> rolesAllowOnTheRoute, String userRole) {
		if(!rolesAllowOnTheRoute.contains(userRole.toUpperCase()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unathorized");
	}
}