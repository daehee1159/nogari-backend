package com.msm.nogari.core.enums;

/**
 * @author 최대희
 * @since 2023-12-20
 * 1.본인 글 작성
 * 2.본인 댓글 작성
 * 3.포인트 지급,차감 이력
 * 4.본인 글에 댓글이 달렸을 때
 * 5.본인 글 좋아요 눌렸을 때
 */
public enum NotificationType {
	COMMON("공통"),
	REGISTRATION("가입"),

	ATTENDANCE("출석"),
	COMMUNITY_BOARD("커뮤니티 글 작성"),
	COMMUNITY_COMMENT("커뮤니티 댓글 작성"),
	REVIEW_BOARD("리뷰 글 작성"),
	REVIEW_COMMENT("리뷰 댓글 작성"),

	WATCH_5SEC_AD("5초 광고 시청"),
	WATCH_30SEC_AD("30초 광고 시청"),
	POINT_PLUS("포인트 적립"),
	POINT_MINUS("포인트 차감");

	private final String text;

	NotificationType(String text) {
		this.text = text;
	}

	public String getText() {
		return this.text;
	}
}
