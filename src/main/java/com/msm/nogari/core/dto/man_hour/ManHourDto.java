package com.msm.nogari.core.dto.man_hour;

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
}
