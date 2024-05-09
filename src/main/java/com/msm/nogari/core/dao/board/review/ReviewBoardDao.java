package com.msm.nogari.core.dao.board.review;

import com.msm.nogari.core.dto.board.review.ReviewBoardDto;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author 최대희
 * @since 2024-05-09
 */
@Getter
public class ReviewBoardDao {
	private Long boardSeq;

	private String title;
	private String rank;
	private String period;
	private String atmosphere;
	private String content;

	private Long memberSeq;
	private String nickname;
	// 핫갤 여부
	private Long viewCnt;
	private Long likeCnt;

	private String hotYN;
	private String noticeYN;

	private String deleteYN;

	private LocalDateTime modDt;
	private LocalDateTime regDt;

	public static ReviewBoardDao of(ReviewBoardDto boardDto) {
		ReviewBoardDao boardDao = new ReviewBoardDao();

		boardDao.boardSeq = boardDto.getBoardSeq();

		boardDao.title = boardDto.getTitle();
		boardDao.rank = boardDto.getRank();
		boardDao.period = boardDto.getPeriod();
		boardDao.atmosphere = boardDto.getAtmosphere();
		boardDao.content = boardDto.getContent();

		boardDao.memberSeq = boardDto.getMemberSeq();
		boardDao.nickname = boardDto.getNickname();

		boardDao.viewCnt = boardDto.getViewCnt();
		boardDao.likeCnt = boardDto.getLikeCnt();

		boardDao.hotYN = boardDto.getHotYN();
		boardDao.noticeYN = boardDto.getNoticeYN();

		boardDao.deleteYN = boardDto.getDeleteYN();

		boardDao.modDt = boardDto.getModDt();
		boardDao.regDt = boardDto.getRegDt();

		return boardDao;
	}
}
