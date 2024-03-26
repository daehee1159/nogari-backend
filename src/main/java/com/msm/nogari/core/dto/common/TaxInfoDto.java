package com.msm.nogari.core.dto.common;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author 최대희
 * @since 2024-02-14
 */
@Getter
@Setter
public class TaxInfoDto {
	private List<IncomeTaxDto> incomeTaxDtoList; // 소득세 클래스
	private double nationalPension; // 국민연금
	private double healthInsurance; // 건강보험
	private double employmentInsurance; // 고용보험

	private double individualIncomeTax = 3.3;
	@Getter
	@Setter
	public static class IncomeTaxDto {
		private int more;
		private int under;
		private int amount;
		private String standardDt;
	}
}
