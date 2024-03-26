package com.msm.nogari.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 최대희
 * @since 2023-11-27
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Value("${app.upload.dir}")
	private String uploadDir;

	@Value("{uploadFile.resourcePath}")
	private String resourcePath;


	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String resourcePattern = resourcePath + "**";

//		registry.addResourceHandler(resourcePattern)
//			.addResourceLocations("file:///" + uploadDir);

		registry.addResourceHandler("/upload/**").addResourceLocations("file:///D:/filetest/").setCachePeriod(31536000);
	}

}
