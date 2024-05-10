package com.msm.nogari.core.dto.man_hour;

import com.msm.nogari.core.dao.man_hour.ManHourDao;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author 최대희
 * @since 2023-11-28
 */
@Getter
@Setter
public class ManHourDto {
	private Long manHourSeq;

	private Long memberSeq;

	private String startDt;
	private String endDt;

	private double totalAmount;
	private double manHour;
	private int unitPrice;
	private int etcPrice;
	private String memo;
	private String isHoliday;

	private LocalDateTime modDt;
	private LocalDateTime regDt;

	public static ManHourDto of(ManHourDao manHourDao) {
		ManHourDto manHourDto = new ManHourDto();

		manHourDto.manHourSeq = manHourDao.getManHourSeq();

		manHourDto.memberSeq = manHourDao.getMemberSeq();

		manHourDto.startDt = manHourDao.getStartDt();
		manHourDto.endDt = manHourDao.getEndDt();

		manHourDto.totalAmount = manHourDao.getTotalAmount();
		manHourDto.manHour = manHourDao.getManHour();
		manHourDto.unitPrice = manHourDao.getUnitPrice();
		manHourDto.etcPrice = manHourDao.getEtcPrice();
		manHourDto.memo = manHourDao.getMemo();
		manHourDto.isHoliday = manHourDao.getIsHoliday();

		manHourDto.modDt = manHourDao.getModDt();
		manHourDto.regDt = manHourDao.getRegDt();

		return manHourDto;
	}
}
