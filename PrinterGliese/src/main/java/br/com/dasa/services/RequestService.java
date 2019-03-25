package br.com.dasa.services;


import java.net.URI;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class RequestService {

	public <T extends Object> ResponseEntity<T> post(String url, T entity, Class<?> classe) {

		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<T> request = new HttpEntity<>(entity);
		return (ResponseEntity<T>) restTemplate.postForEntity(URI.create(url), request, classe);

	}

	public <T, E extends Object> ResponseEntity<T> put(String url, E entity, Class<?> classe) {
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>(); 
		map.add(HttpHeaders.CONTENT_TYPE.intern(), MediaType.APPLICATION_JSON_VALUE.intern());
		
		HttpEntity<E> request = new HttpEntity<>(entity, map);
	
		return (ResponseEntity<T>) restTemplate.exchange(URI.create(url), HttpMethod.PUT, request, classe);

	}

	public <T extends Object> ResponseEntity<T> get(String url, Class<?> classe) {
		RequestEntity<T> resquest = new RequestEntity<>(HttpMethod.GET, URI.create(url));
		RestTemplate restTemplate = new RestTemplate();
		return (ResponseEntity<T>) restTemplate.exchange(resquest, classe);

	}

	
}
