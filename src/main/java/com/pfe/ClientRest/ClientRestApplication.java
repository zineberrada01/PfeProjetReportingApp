package com.pfe.ClientRest;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
/*import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
*/import org.springframework.web.client.RestTemplate;

import com.pfe.ClientRest.controller.Ccontroller;
/*import com.pfe.ClientRest.property.FilesStorageProperties;*/

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan({"com.pfe"})
/* @EnableConfigurationProperties({FilesStorageProperties.class}) */

public class ClientRestApplication {

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	public static void main(String[] args) {
		
		/* SpringApplication.run(ClientRestApplication.class, args); */
		SpringApplicationBuilder builder = new SpringApplicationBuilder(ClientRestApplication.class);
		builder.headless(false).run(args);
		
		
		
		

}
}
