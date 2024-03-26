package com.msm.nogari.core.dto.jwt;

import com.msm.nogari.core.jwt.JwtDto;

/**
 * @author 최대희
 * @since 2023-12-14
 */
public class AuthenticationResponse {
	private JwtDto jwtDto;

	public AuthenticationResponse(JwtDto jwtDto) {
		super();
		this.jwtDto = jwtDto;
	}

	public JwtDto getJwt() {
		return jwtDto;
	}

	public void setJwt(JwtDto jwtDto) {
		this.jwtDto = jwtDto;
	}

	@Override
	public String toString() {
		return "AuthenticationResponse [jwt=" + jwtDto + "]";
	}
}
