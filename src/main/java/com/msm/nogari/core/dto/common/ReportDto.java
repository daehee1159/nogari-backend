package com.msm.nogari.core.dto.common;

import com.msm.nogari.core.enums.ReportReason;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 최대희
 * @since 2024-03-11
 */
@Getter
@Setter
public class ReportDto {
	private Long reportSeq;

	private BoardType boardType;
	private Long boardSeq;
	private ReportReason reportReason;

	private Long reporterMemberSeq;
	private Long reportedMemberSeq;

	private String regDt;

	public static enum BoardType {
		COMMUNITY,
		REVIEW,
		COMMUNITY_COMMENT,
		REVIEW_COMMENT,
		COMMUNITY_CHILD_COMMENT,
		REVIEW_CHILD_COMMENT
	}
}
