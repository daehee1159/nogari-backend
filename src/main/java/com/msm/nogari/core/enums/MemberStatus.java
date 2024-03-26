package com.msm.nogari.core.enums;

/**
 * @author 최대희
 * @since 2023-11-28
 */
public enum MemberStatus {
	UNSUBSCRIBED("미가입"),

	UNIDENTIFIED("미확인"),

	ACTIVE("활동중"),

	INACTIVE("휴면"),

	WITHDRAWAL("탈퇴");

	private final String text;

	MemberStatus(String text) {
		this.text = text;
	}

	public String getText() {
		return this.text;
	}
}
