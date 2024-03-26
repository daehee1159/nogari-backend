package com.msm.nogari.core.dto.board.community;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 최대희
 * @since 2023-11-28
 */
@Getter
@Setter
public class BoardLikeDto {
	private Long boardLikeSeq;

	private Long boardSeq;
	private Long memberSeq;

	private String regDt;
}
