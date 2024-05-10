package com.msm.nogari.core.dto.man_hour;

import com.msm.nogari.core.dao.man_hour.ManHourHistoryDao;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author 최대희
 * @since 2023-12-11
 * DB Column 과는 상관 없는 DTO
 */
@Getter
@Setter
public class ManHourHistoryDto {
	private Long manHourHistorySeq;

	private Long memberSeq;

	private LocalDateTime startDt;
	private LocalDateTime endDt;

	private double totalManHour;
	private int totalUniPrice;
	private int totalEtcPrice;
	private double totalAmount;

	public static ManHourHistoryDto of(ManHourHistoryDao manHourHistoryDao) {
		ManHourHistoryDto manHourHistoryDto = new ManHourHistoryDto();

		manHourHistoryDto.manHourHistorySeq = manHourHistoryDao.getManHourHistorySeq();

		manHourHistoryDto.memberSeq = manHourHistoryDao.getMemberSeq();

		manHourHistoryDto.startDt = manHourHistoryDao.getStartDt();
		manHourHistoryDto.endDt = manHourHistoryDao.getEndDt();

		manHourHistoryDto.totalManHour = manHourHistoryDao.getTotalManHour();
		manHourHistoryDto.totalUniPrice = manHourHistoryDao.getTotalUniPrice();
		manHourHistoryDto.totalEtcPrice = manHourHistoryDao.getTotalEtcPrice();
		manHourHistoryDto.totalAmount = manHourHistoryDao.getTotalAmount();

		return manHourHistoryDto;
	}
}
