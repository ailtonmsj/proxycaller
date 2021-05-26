package br.com.amsj.proxycaller.client;

import org.springframework.cloud.openfeign.FeignClient;

import feign.RequestLine;

@FeignClient("clientRedundante")
public interface SimpleCallerRedundantClient {

	@RequestLine("GET /")
	String getCurrentDateRedundant();

}
