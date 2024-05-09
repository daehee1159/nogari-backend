package com.msm.nogari.core.dao.man_hour;

import com.msm.nogari.core.dto.man_hour.ManHourHistoryDto;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author 최대희
 * @since 2024-05-09
 */
@Getter
public class ManHourHistoryDao {
	private Long manHourHistorySeq;

	private Long memberSeq;

	private LocalDateTime startDt;
	private LocalDateTime endDt;

	private double totalManHour;
	private int totalUniPrice;
	private int totalEtcPrice;
	private double totalAmount;

	public static ManHourHistoryDao of(ManHourHistoryDto manHourHistoryDto) {
		ManHourHistoryDao manHourHistoryDao = new ManHourHistoryDao();

		manHourHistoryDao.manHourHistorySeq =manHourHistoryDto.getManHourHistorySeq();

		manHourHistoryDao.memberSeq =manHourHistoryDto.getMemberSeq();

		manHourHistoryDao.startDt =manHourHistoryDto.getStartDt();
		manHourHistoryDao.endDt =manHourHistoryDto.getEndDt();

		manHourHistoryDao.totalManHour =manHourHistoryDto.getTotalManHour();
		manHourHistoryDao.totalUniPrice =manHourHistoryDto.getTotalUniPrice();
		manHourHistoryDao.totalEtcPrice =manHourHistoryDto.getTotalEtcPrice();
		manHourHistoryDao.totalAmount =manHourHistoryDto.getTotalAmount();

		return manHourHistoryDao;
	}
}
