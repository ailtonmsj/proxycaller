package br.com.amsj.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.amsj.client.SimpleCallerClient;
import feign.Feign;
import feign.okhttp.OkHttpClient;

@Component
public class SchedulerCall {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Value("${remoteURL}")
	String remoteUrl; 
	
	@Scheduled(fixedRate = 1000)
	public void scheduleCall() {
		
		SimpleCallerClient client = Feign.builder().client(new OkHttpClient()).target(SimpleCallerClient.class, remoteUrl);
		
		String date = client.getCurrentDate();
		
		log.info("date - " + date);
	}
}