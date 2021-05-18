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
import feign.Feign;
import feign.okhttp.OkHttpClient;

@RestController
public class ProxyCaller {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Value("${remoteURL}")
	String remoteUrl; 
	
	@RequestMapping(method=RequestMethod.GET, path="/")
	public String proxy(HttpServletRequest request) {
		
		log.info("begin proxy");
		
		Enumeration<String> headerNames = request.getHeaderNames();
		
		Iterator<String> iterator = headerNames.asIterator();
		
		while(iterator.hasNext()) {
			String headerName = iterator.next();
			log.info("headerName --> " + headerName + " - " +  request.getHeader(headerName));
		}
		
		SimpleCallerClient client = Feign.builder().client(new OkHttpClient()).target(SimpleCallerClient.class, remoteUrl);
		
		String date = client.getCurrentDate();
		
		log.info("date - " + date);
		
		log.info("end proxy");
		
		return date;
	}
}