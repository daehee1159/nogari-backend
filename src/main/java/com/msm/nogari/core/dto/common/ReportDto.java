package com.msm.nogari.core.dto.common;

import com.msm.nogari.core.dao.common.ReportDao;
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

	private ReportDao.BoardType boardType;
	private Long boardSeq;
	private ReportReason reportReason;

	private Long reporterMemberSeq;
	private Long reportedMemberSeq;

	private String regDt;

}
