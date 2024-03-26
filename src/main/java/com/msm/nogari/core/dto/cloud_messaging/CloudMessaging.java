package com.msm.nogari.core.dto.cloud_messaging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * @author 최대희
 * @since 2023-11-28
 */
@Builder
@Getter
@AllArgsConstructor
public class CloudMessaging {
	private boolean validate_only;
	private Message message;

	@Builder
	@AllArgsConstructor
	@Getter
	public static class Message {
		private Notification notification;
		private String token;
		private Data data;
		private Android android;
		private Apns apns;
	}

	@Builder
	@AllArgsConstructor
	@Getter
	public static class Android {
		private String priority;
	}

	@Builder
	@AllArgsConstructor
	@Getter
	public static class Apns {
		private String priority;
		private boolean content_available;
	}

	@Builder
	@AllArgsConstructor
	@Getter
	public static class Notification {
		private String title;
		private String body;
		private String image;
	}

	@Builder
	@AllArgsConstructor
	@Getter
	public static class Data {
		private String click_action;
		private String sound;
		private String status;
		private String screen;

		// 시그널 메시징인지 확인
		private String isSignal;

		// 시그널을 주고 받을 때 필요한 정보들
		private String position;
		private String category;
		private String tryCount;

		private String eatSignalSeq;
		private String playSignalSeq;
		private String tempSignalSeq;
		private String messageOfTheDaySeq;
		private String todaySignalSeq;

		private String senderSelected;
		private String recipientSelected;
		// 오늘의 한마디
		private String message;
		private String termination;
		private String result;
		private String resultSelected;
	}

}
