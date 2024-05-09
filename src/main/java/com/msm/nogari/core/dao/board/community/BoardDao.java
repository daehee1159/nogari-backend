package com.msm.nogari.core.dao.board.community;

import com.msm.nogari.core.dto.board.community.BoardDto;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author 최대희
 * @since 2024-05-09
 */
@Getter
public class BoardDao {
	private Long boardSeq;

	private String title;
	private String content;

	private String fileName1;
	private String fileName2;
	private String fileName3;

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

	public static BoardDao of(BoardDto boardDto) {
		BoardDao boardDao = new BoardDao();

		boardDao.boardSeq = boardDto.getBoardSeq();

		boardDao.title = boardDto.getTitle();
		boardDao.content = boardDto.getContent();

		boardDao.fileName1 = boardDto.getFileName1();
		boardDao.fileName2 = boardDto.getFileName2();
		boardDao.fileName3 = boardDto.getFileName3();

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
