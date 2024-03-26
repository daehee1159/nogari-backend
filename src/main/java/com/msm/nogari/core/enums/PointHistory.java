package com.msm.nogari.core.enums;

import com.msm.nogari.core.dto.member.PointHistoryDto;
import lombok.Getter;

/**
 * @author 최대희
 * @since 2023-11-30
 */
@Getter
public enum PointHistory {
	ATTENDANCE("출석", 10),

	COMMUNITY_WRITING("갤러리 글 작성", 10),
	REVIEW_WRITING("리뷰 글 작성", 30),

	COMMUNITY_COMMENT("갤러리 댓글 작성", 5),
	REVIEW_COMMENT("리뷰 댓글 작성", 5),

	WATCH_5SEC_AD("5초 광고 시청", 10),
	WATCH_30SEC_AD("30초 광고 시청", 20),

	ETC("아직 몰라", 160);

	private final String text;
	private final int point;

	PointHistory(String text, int point) {
		this.text = text;
		this.point = point;
	}

	// 이미 자동 매칭되서 오기 때문에 이 함수는 필요 없을듯?
	public static PointHistory getPointHistory(String pointType) {
		for (PointHistory pointHistory : PointHistory.values()) {
			if (pointHistory.name().equalsIgnoreCase(pointType)) {
				return pointHistory;
			}
		}
		throw new IllegalArgumentException("Invalid value for PointHistory: ");
	}

	public static int getPoint(PointHistory history) {
		for (PointHistory pointHistory : PointHistory.values()) {
			if (pointHistory == history) {
				return pointHistory.getPoint();
			}
		}
		throw new IllegalArgumentException("Invalid value for PointHistory: ");
	}

	/**
	 * PointHistory 로 PointHistoryDto 반환
	 */
	public static PointHistoryDto getResultComment(PointHistoryDto pointHistoryDto) {
		PointHistoryDto resultDto = new PointHistoryDto();
		resultDto.setMemberSeq(pointHistoryDto.getMemberSeq());
		resultDto.setPointHistory(pointHistoryDto.getPointHistory());
		resultDto.setPoint(getPoint(pointHistoryDto.getPointHistory()));
		resultDto.setHistoryComment(pointHistoryDto.getPointHistory().getText());
		resultDto.setBoardSeq(pointHistoryDto.getBoardSeq());

		if (resultDto.getPoint() > 0) {
			resultDto.setResultComment(resultDto.getHistoryComment() + " +" + resultDto.getPoint() + "P 적립");
		} else {
			resultDto.setResultComment(resultDto.getHistoryComment() + " -" + resultDto.getPoint() + "P 차감");
		}

		return resultDto;
	}
}
