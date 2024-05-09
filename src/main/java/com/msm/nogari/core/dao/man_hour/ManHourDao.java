package com.msm.nogari.core.dao.man_hour;

import com.msm.nogari.core.dto.man_hour.ManHourDto;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author 최대희
 * @since 2024-05-09
 */
@Getter
public class ManHourDao {
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

	public static ManHourDao of(ManHourDto manHourDto) {
		ManHourDao manHourDao = new ManHourDao();

		manHourDao.manHourSeq = manHourDto.getManHourSeq();

		manHourDao.memberSeq = manHourDto.getMemberSeq();

		manHourDao.startDt = manHourDto.getStartDt();
		manHourDao.endDt = manHourDto.getEndDt();

		manHourDao.totalAmount = manHourDto.getTotalAmount();
		manHourDao.manHour = manHourDto.getManHour();
		manHourDao.unitPrice = manHourDto.getUnitPrice();
		manHourDao.etcPrice = manHourDto.getEtcPrice();
		manHourDao.memo = manHourDto.getMemo();
		manHourDao.isHoliday = manHourDto.getIsHoliday();

		manHourDao.modDt = manHourDto.getModDt();
		manHourDao.regDt = manHourDto.getRegDt();

		return manHourDao;
	}
}
