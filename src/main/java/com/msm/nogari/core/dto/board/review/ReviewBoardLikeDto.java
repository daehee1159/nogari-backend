package com.msm.nogari.core.dto.board.review;

import com.msm.nogari.core.dao.board.review.ReviewBoardLikeDao;
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

	public static ReviewBoardLikeDto of(ReviewBoardLikeDao boardLikeDao) {
		ReviewBoardLikeDto boardLikeDto = new ReviewBoardLikeDto();

		boardLikeDto.boardLikeSeq = boardLikeDao.getBoardLikeSeq();

		boardLikeDto.boardSeq = boardLikeDao.getBoardSeq();
		boardLikeDto.memberSeq = boardLikeDao.getMemberSeq();

		boardLikeDto.regDt = boardLikeDao.getRegDt();

		return boardLikeDto;
	}
}
