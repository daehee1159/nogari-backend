package com.msm.nogari.core.dto.jwt;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 최대희
 * @since 2023-12-14
 */
@Getter
@Setter
public class AuthenticationMemberDto {
	private Long memberSeq;
	private String nickName;
	private String email;
}
