package com.msm.nogari.core.dto.board.community;

import com.msm.nogari.core.dao.board.community.BoardDao;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author 최대희
 * @since 2023-11-28
 */
@Getter
@Setter
public class BoardDto {
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
	// DB 컬럼에는 필요 없는 백엔드에서 처리해서 프론트에 리턴해줄 필드
	private boolean isMyPress;
	private String hotYN;
	private String noticeYN;

	private String deleteYN;

	private LocalDateTime modDt;
	private LocalDateTime regDt;

	public static BoardDto of(BoardDao boardDao) {
		BoardDto boardDto = new BoardDto();

		boardDto.boardSeq = boardDao.getBoardSeq();

		boardDto.title = boardDao.getTitle();
		boardDto.content = boardDao.getContent();

		boardDto.fileName1 = boardDao.getFileName1();
		boardDto.fileName2 = boardDao.getFileName2();
		boardDto.fileName3 = boardDao.getFileName3();

		boardDto.memberSeq = boardDao.getMemberSeq();
		boardDto.nickname = boardDao.getNickname();

		boardDto.viewCnt = boardDao.getViewCnt();
		boardDto.likeCnt = boardDao.getLikeCnt();

		boardDto.hotYN = boardDao.getHotYN();
		boardDto.noticeYN = boardDao.getNoticeYN();

		boardDto.deleteYN = boardDao.getDeleteYN();

		boardDto.modDt = boardDao.getModDt();
		boardDto.regDt = boardDao.getRegDt();

		return boardDto;
	}
}
