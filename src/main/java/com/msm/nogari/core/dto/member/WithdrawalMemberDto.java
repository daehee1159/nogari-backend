package com.msm.nogari.core.dto.member;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 최대희
 * @since 2023-11-29
 */
@Getter
@Setter
public class WithdrawalMemberDto {
	private Long memberSeq;

	private String reasonMessage;
}
