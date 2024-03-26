package com.msm.nogari.core.dto.man_hour;

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

}
