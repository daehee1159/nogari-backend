package com.msm.nogari.util;

import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 최대희
 * @since 2023-11-27
 */
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8180" })
public class HttpUtil {
	public static String getRequestURI( HttpServletRequest request) {

		return request.getRequestURI();
	}
}
