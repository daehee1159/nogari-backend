package com.msm.nogari.core.dao.member;

import com.msm.nogari.core.dto.member.NotificationDto;
import com.msm.nogari.core.enums.NotificationType;
import lombok.Getter;

/**
 * @author 최대희
 * @since 2024-05-09
 */
@Getter
public class NotificationDao {
	private Long notificationSeq;

	private Long memberSeq;

	private NotificationType type;
	private String message;

	// type 이 뭐냐에 따라 커뮤니티인지 리뷰인지 구별할 수 있음, 둘 다 아닐때는 0
	private Long boardSeq;
	private String regDt;
	// 일단은 사용X 그러나 추후 notification bell icon 읽음 표시를 위해 남겨둠
	private String readDt;

	public static NotificationDao of(NotificationDto notificationDto) {
		NotificationDao notificationDao = new NotificationDao();

		notificationDao.notificationSeq = notificationDto.getNotificationSeq();

		notificationDao.memberSeq = notificationDto.getMemberSeq();

		notificationDao.type = notificationDto.getType();
		notificationDao.message = notificationDto.getMessage();

		validate(notificationDto.getType(), notificationDto.getBoardSeq());
		notificationDao.boardSeq = notificationDto.getBoardSeq();
		notificationDao.regDt = notificationDto.getRegDt();

		notificationDao.readDt = notificationDto.getReadDt();

		return notificationDao;
	}

	private static void validate(NotificationType type, Long boardSeq) {
		if (boardSeq == 0L && type == NotificationType.COMMUNITY_BOARD || type == NotificationType.COMMUNITY_COMMENT || type == NotificationType.REVIEW_BOARD || type == NotificationType.REVIEW_COMMENT) {
			throw new IllegalArgumentException("IllegalArgumentException boardSeq must not be 0 : this boardSeq = " + boardSeq);
		}
	}
}
