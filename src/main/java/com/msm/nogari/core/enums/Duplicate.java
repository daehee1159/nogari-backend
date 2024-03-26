package com.msm.nogari.core.enums;

/**
 * @author 최대희
 * @since 2023-12-28
 */
public enum Duplicate {
	DUPLICATE("중복"),

	NON_DUPLICATE("미중복");
	private final String text;

	Duplicate(String text) {
		this.text = text;
	}

	public String getText() {
		return this.text;
	}
}
