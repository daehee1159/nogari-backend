package com.msm.nogari.core.dao.board.community;

import com.msm.nogari.core.dto.board.community.BoardLikeDto;
import lombok.Getter;

/**
 * @author 최대희
 * @since 2024-05-09
 */
@Getter
public class BoardLikeDao {
	private Long boardLikeSeq;

	private Long boardSeq;
	private Long memberSeq;

	private String regDt;

	public static BoardLikeDao of(BoardLikeDto boardLikeDto) {
		BoardLikeDao boardLikeDao = new BoardLikeDao();

		boardLikeDao.boardLikeSeq = boardLikeDto.getBoardLikeSeq();

		boardLikeDao.boardSeq = boardLikeDto.getBoardSeq();
		boardLikeDao.memberSeq = boardLikeDto.getMemberSeq();

		boardLikeDao.regDt = boardLikeDto.getRegDt();

		return boardLikeDao;
	}
}
