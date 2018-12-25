package com.meta.filemeta.util;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileUploadConfiguration {

	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		// 设置文件大小限制 ,

		factory.setMaxFileSize("500000KB"); // KB,MB
		/// 设置总上传数据总大小
		factory.setMaxRequestSize("500000KB");

		return factory.createMultipartConfig();
	}
}
