package br.com.amsj.proxycaller.client;

import org.springframework.cloud.openfeign.FeignClient;

import feign.RequestLine;

@FeignClient("client")
public interface SimpleCallerClient {

	@RequestLine("GET /")
	String getCurrentDate();

}
