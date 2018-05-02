package com.spark.ims.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

@Configuration
public class MultipartFileConfig {

	@Value("${multipart.maxFileSize}")
	private String maxFileSize;
	@Value("${multipart.maxRequestSize}")
	private String maxRequestSize;

	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(this.maxFileSize);
        factory.setMaxRequestSize(this.maxRequestSize);
        return factory.createMultipartConfig();
	}

}
