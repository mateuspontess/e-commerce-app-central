package br.com.ecommerce.gateway.routes;

import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpMethod;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class RequestInfo {

	private Set<String> pathSet = new HashSet<>();
	private HttpMethod requestMethod;
	
	public RequestInfo(String path, HttpMethod method) {
		Set<String> setString = this.setPath(path);
		
		setString.forEach(element -> {
			boolean isNumber = element.matches("^\\d+$");
		    
			element = isNumber ? element.replace(element, "dynamicNumber") : element;
			this.pathSet.add(element);
		});
		this.requestMethod = method;
	}
	
	private Set<String> setPath(String path) {
		Set<String> set = new HashSet<>();
        StringBuilder segment = new StringBuilder();

        for (int i = 0; i < path.length(); i++) {
            char c = path.charAt(i);
            if (c == '/') {
                if (segment.length() > 0) {
                	set.add(segment.toString());
                    segment.setLength(0);
                }
                set.add("/");
            } else {
                segment.append(c);
            }
        }

        if (segment.length() > 0)
        	set.add(segment.toString());
        
        return set;
    }
}