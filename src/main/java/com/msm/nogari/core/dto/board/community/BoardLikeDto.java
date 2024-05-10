package com.msm.nogari.core.dto.board.community;

import com.msm.nogari.core.dao.board.community.BoardLikeDao;
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

	public static BoardLikeDto of(BoardLikeDao boardLikeDao) {
		BoardLikeDto boardLikeDto = new BoardLikeDto();

		boardLikeDto.boardLikeSeq = boardLikeDao.getBoardLikeSeq();

		boardLikeDto.boardSeq = boardLikeDao.getBoardSeq();
		boardLikeDto.memberSeq = boardLikeDao.getMemberSeq();

		boardLikeDto.regDt = boardLikeDao.getRegDt();

		return boardLikeDto;
	}
}
