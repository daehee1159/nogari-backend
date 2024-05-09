package com.msm.nogari.core.dao.common;

import com.msm.nogari.core.dto.common.ReportDto;
import com.msm.nogari.core.enums.ReportReason;
import lombok.Getter;

/**
 * @author 최대희
 * @since 2024-05-09
 */
@Getter
public class ReportDao {
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

	private static ReportDao of(ReportDto reportDto) {
		ReportDao reportDao = new ReportDao();
		reportDao.reportSeq = reportDto.getReportSeq();

		reportDao.boardType = reportDto.getBoardType();
		reportDao.boardSeq = reportDto.getBoardSeq();
		reportDao.reportReason = reportDto.getReportReason();

		reportDao.reporterMemberSeq = reportDto.getReporterMemberSeq();
		reportDao.reportedMemberSeq = reportDto.getReportedMemberSeq();

		return reportDao;
	}
}
