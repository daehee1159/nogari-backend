package com.msm.nogari.core.dto.member;

import com.msm.nogari.core.dao.member.PointHistoryDao;
import com.msm.nogari.core.enums.PointHistory;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 최대희
 * @since 2023-11-30
 * 포인트 이력
 */
@Getter
@Setter
public class PointHistoryDto {
	private Long pointHistorySeq;

	private Long memberSeq;

	private int point;
	private PointHistory pointHistory;
	private String historyComment;
	private String resultComment;

	private Long boardSeq;
	private String regDt;

	public static PointHistoryDto of(PointHistoryDao pointHistoryDao) {
		PointHistoryDto pointHistoryDto = new PointHistoryDto();

		pointHistoryDto.pointHistorySeq = pointHistoryDao.getPointHistorySeq();

		pointHistoryDto.memberSeq = pointHistoryDao.getMemberSeq();

		pointHistoryDto.point = pointHistoryDao.getPoint();
		pointHistoryDto.pointHistory = pointHistoryDao.getPointHistory();
		pointHistoryDto.historyComment = pointHistoryDao.getHistoryComment();
		pointHistoryDto.resultComment = pointHistoryDao.getResultComment();

		pointHistoryDto.boardSeq = pointHistoryDao.getBoardSeq();
		pointHistoryDto.regDt = pointHistoryDao.getRegDt();

		return pointHistoryDto;
	}
}
