package br.com.amsj.proxycaller.controller;

import java.util.Enumeration;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.amsj.proxycaller.client.SimpleCallerClient;
import br.com.amsj.proxycaller.client.SimpleCallerRedundantClient;
import feign.Feign;
import feign.okhttp.OkHttpClient;

@RestController
public class ProxyCaller {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Value("${remoteURL}")
	String remoteUrl; 
	
	@Value("${fallbackRemoteURL}")
	String fallbackRemoteURL;
	
	@RequestMapping(method=RequestMethod.GET, path="/")
	public String proxy(HttpServletRequest request) {
		
		log.info("begin proxy");
		
		Enumeration<String> headerNames = request.getHeaderNames();
		
		Iterator<String> iterator = headerNames.asIterator();
		
		while(iterator.hasNext()) {
			String headerName = iterator.next();
			log.info("headerName --> " + headerName + " - " +  request.getHeader(headerName));
		}
		
		String date = "";
		
		// A LOGICA DO TRY CATCH EH PARA NAO PRECISAR DO ENVOY FILTER DIECIONANDO PARA O FALLBACK
		try {
			SimpleCallerClient client = Feign.builder().client(new OkHttpClient()).target(SimpleCallerClient.class, remoteUrl);
			date = client.getCurrentDate();
		}catch (Exception e) {
			log.error("Obtendo valores do fallback");
			
			SimpleCallerRedundantClient redundantClient = Feign.builder().client(new OkHttpClient()).target(SimpleCallerRedundantClient.class, fallbackRemoteURL);
			date = redundantClient.getCurrentDateRedundant();
		}
		
		log.info("date - " + date);
		
		log.info("end proxy");
		
		return date;
	}
}