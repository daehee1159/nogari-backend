package com.msm.nogari.core.enums;

/**
 * @author 최대희
 * @since 2024-03-11
 */
public enum ReportReason {
	INCORRECT_INFORMATION("잘못된 정보"),

	COMMERCIAL_ADVERTISING("상업적 광고"),

	PORNOGRAPHY("음란물"),

	VIOLENCE("폭력성"),

	ETC("기타");

	private final String text;

	ReportReason(String text) {
		this.text = text;
	}

	public String getText() {
		return this.text;
	}
}
