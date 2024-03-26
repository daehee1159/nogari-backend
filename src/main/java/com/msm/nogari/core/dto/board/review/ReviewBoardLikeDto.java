package com.msm.nogari.core.dto.board.review;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author 최대희
 * @since 2023-11-28
 */
@Getter
@Setter
public class ReviewBoardLikeDto {
	private Long boardLikeSeq;

	private Long boardSeq;
	private Long memberSeq;

	private LocalDateTime regDt;
}
