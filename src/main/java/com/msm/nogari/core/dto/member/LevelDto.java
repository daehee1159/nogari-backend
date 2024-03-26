package com.msm.nogari.core.dto.member;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 최대희
 * @since 2024-01-22
 */
@Getter
@Setter
public class LevelDto {
	private Long levelSeq;

	private Long memberSeq;
	private int level;
	private int point;

	private String modDt;
	private String regDt;
}
