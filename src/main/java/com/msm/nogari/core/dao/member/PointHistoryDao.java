package com.msm.nogari.core.dao.member;

import com.msm.nogari.core.dto.member.PointHistoryDto;
import com.msm.nogari.core.enums.PointHistory;
import lombok.Getter;

/**
 * @author 최대희
 * @since 2024-05-09
 */
@Getter
public class PointHistoryDao {
	private Long pointHistorySeq;

	private Long memberSeq;

	private int point;
	private PointHistory pointHistory;
	private String historyComment;
	private String resultComment;

	private Long boardSeq;
	private String regDt;

	public static PointHistoryDao of(PointHistoryDto pointHistoryDto) {
		PointHistoryDao pointHistoryDao = new PointHistoryDao();

		pointHistoryDao.pointHistorySeq = pointHistoryDto.getPointHistorySeq();

		pointHistoryDao.memberSeq = pointHistoryDto.getMemberSeq();

		pointHistoryDao.point = pointHistoryDto.getPoint();
		pointHistoryDao.pointHistory = pointHistoryDto.getPointHistory();
		pointHistoryDao.historyComment = pointHistoryDto.getHistoryComment();
		pointHistoryDao.resultComment = pointHistoryDto.getResultComment();

		pointHistoryDao.boardSeq = pointHistoryDto.getBoardSeq();
		pointHistoryDao.regDt = pointHistoryDto.getRegDt();

		return pointHistoryDao;
	}
}
