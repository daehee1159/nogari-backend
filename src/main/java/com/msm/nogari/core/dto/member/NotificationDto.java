package com.msm.nogari.core.dto.member;

import com.msm.nogari.core.enums.NotificationType;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 최대희
 * @since 2023-12-20
 */
@Getter
@Setter
public class NotificationDto {
	private Long notificationSeq;

	private Long memberSeq;

	private NotificationType type;
	private String message;
//	private int point;

	// type 이 뭐냐에 따라 커뮤니티인지 리뷰인지 구별할 수 있음, 둘 다 아닐때는 0
	private Long boardSeq;
	private String regDt;
	// 일단은 사용X 그러나 추후 notification bell icon 읽음 표시를 위해 남겨둠
	private String readDt;

}
