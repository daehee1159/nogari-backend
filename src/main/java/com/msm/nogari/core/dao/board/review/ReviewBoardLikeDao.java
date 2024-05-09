package com.msm.nogari.core.dao.board.review;

import com.msm.nogari.core.dto.board.review.ReviewBoardLikeDto;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author 최대희
 * @since 2024-05-09
 */
@Getter
public class ReviewBoardLikeDao {
	private Long boardLikeSeq;

	private Long boardSeq;
	private Long memberSeq;

	private LocalDateTime regDt;

	public static ReviewBoardLikeDao of(ReviewBoardLikeDto boardDto) {
		ReviewBoardLikeDao boardDao = new ReviewBoardLikeDao();

		boardDao.boardLikeSeq = boardDto.getBoardLikeSeq();

		boardDao.boardSeq = boardDto.getBoardSeq();
		boardDao.memberSeq = boardDto.getMemberSeq();

		boardDao.regDt = boardDto.getRegDt();

		return boardDao;
	}
}
