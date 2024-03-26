package com.msm.nogari.core.dto.common;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 최대희
 * @since 2024-02-16
 */
@Getter
@Setter
public class AppVersionDto {
	private Long appVersionSeq;
	private String android;
	private String ios;
	private String regDt;
}
