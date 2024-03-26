package com.msm.nogari.core.dto.board.community;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 최대희
 * @since 2023-12-03
 */
@Getter
@Setter
public class ChildCommentDto {
	private Long childCommentSeq;

	private Long boardSeq;
	private Long commentSeq;
	private Long memberSeq;
	private String nickname;

	private String content;

	private String deleteYN;

	private String modDt;
	private String regDt;
}
