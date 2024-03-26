package com.msm.nogari.core.dto.board.community;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 최대희
 * @since 2023-11-28
 */
@Getter
@Setter
public class CommentDto {
	private Long commentSeq;

	private Long boardSeq;
	private Long memberSeq;
	private String nickname;

	private String content;

	private String deleteYN;

	private String modDt;
	private String regDt;
}
