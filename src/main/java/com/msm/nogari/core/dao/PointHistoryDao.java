package com.msm.nogari.core.dao;

import com.msm.nogari.core.dto.member.PointHistoryDto;
import com.msm.nogari.core.enums.PointHistory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author 최대희
 * @since 2023-12-26
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointHistoryDao {
	private Long pointHistorySeq;

	private Long memberSeq;

	private int point;
	private PointHistory pointHistory;
	private String historyComment;
	private String resultComment;

	private LocalDateTime regDt;

	public static PointHistoryDao of(PointHistoryDto pointHistoryDto, String pointType) {
		PointHistoryDao pointHistoryDao = new PointHistoryDao();

		pointHistoryDao.memberSeq = pointHistoryDto.getMemberSeq();



		return pointHistoryDao;
	}

}
