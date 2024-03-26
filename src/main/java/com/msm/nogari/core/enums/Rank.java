package com.msm.nogari.core.enums;

import lombok.Getter;

/**
 * @author 최대희
 * @since 2023-11-29
 */
@Getter
public enum Rank {
	PRIVATE("이등병", 0),
	PRIVATE_FIRST("일병", 10),
	CORPORAL("상병", 20),
	SERGEANT("병장", 30),

	STAFF_SERGEANT("하사", 40),
	SERGEANT_FIRST("중사", 50),
	FIRST_SERGEANT("상사", 60),

	SECOND_LIEUTENANT("소위", 70),
	FIRST_LIEUTENANT("중위", 80),
	CAPTAIN("대위", 90),

	MAJOR("소령", 100),
	LIEUTENANT_COLONEL("중령", 110),
	COLONEL("대령", 120),

	BRIGADIER_GENERAL("준장", 130),
	MAJOR_GENERAL("소장", 140),
	LIEUTENANT_GENERAL("중장", 150),
	GENERAL("대장", 160);

	private final String text;
	private final int point;

	Rank(String text, int point) {
		this.text = text;
		this.point = point;
	}

	public static Rank getRankByPoint(int point) {
		if (point >= 160) {
			return Rank.GENERAL;
		} else if (point >= 150) {
			return Rank.LIEUTENANT_GENERAL;
		} else if (point >= 140) {
			return Rank.MAJOR_GENERAL;
		} else if (point >= 130) {
			return Rank.BRIGADIER_GENERAL;
		} else if (point >= 120) {
			return Rank.COLONEL;
		} else if (point >= 110) {
			return Rank.LIEUTENANT_COLONEL;
		} else if (point >= 100) {
			return Rank.MAJOR;
		} else if (point >= 90) {
			return Rank.CAPTAIN;
		} else if (point >= 80) {
			return Rank.FIRST_LIEUTENANT;
		} else if (point >= 70) {
			return Rank.SECOND_LIEUTENANT;
		} else if (point >= 60) {
			return Rank.FIRST_SERGEANT;
		} else if (point >= 50) {
			return Rank.SERGEANT_FIRST;
		} else if (point >= 40) {
			return Rank.STAFF_SERGEANT;
		} else if (point >= 30) {
			return Rank.SERGEANT;
		} else if (point >= 20) {
			return Rank.CORPORAL;
		} else if (point >= 10) {
			return Rank.PRIVATE_FIRST;
		} else {
			return Rank.PRIVATE;
		}
	}
}
