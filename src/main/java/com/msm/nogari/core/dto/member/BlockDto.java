package com.msm.nogari.core.dto.member;

import lombok.Getter;

/**
 * @author 최대희
 * @since 2024-03-12
 */
@Getter
public class BlockDto {
	private Long blockSeq;
	private Long memberSeq;
	private Long blockMemberSeq;
	private String blockMemberNickname;
	private String regDt;
}
