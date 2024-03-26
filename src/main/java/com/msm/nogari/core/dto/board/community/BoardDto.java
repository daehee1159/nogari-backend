package com.msm.nogari.core.dto.board.community;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author 최대희
 * @since 2023-11-28
 */
@Getter
@Setter
public class BoardDto {
	private Long boardSeq;

	private String title;
	private String content;

	private String fileName1;
	private String fileName2;
	private String fileName3;

	private Long memberSeq;
	private String nickname;
	// 핫갤 여부
	private Long viewCnt;
	private Long likeCnt;
	// DB 컬럼에는 필요 없는 백엔드에서 처리해서 프론트에 리턴해줄 필드
	private boolean isMyPress;
	private String hotYN;
	private String noticeYN;

	private String deleteYN;

	private LocalDateTime modDt;
	private LocalDateTime regDt;
}
