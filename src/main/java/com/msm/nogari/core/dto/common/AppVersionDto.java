package com.msm.nogari.core.dto.common;

import com.msm.nogari.core.dao.common.AppVersionDao;
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

	public static AppVersionDto of(AppVersionDao appVersionDao) {
		AppVersionDto appVersionDto = new AppVersionDto();
		appVersionDto.appVersionSeq = appVersionDao.getAppVersionSeq();
		appVersionDto.android = appVersionDao.getAndroid();
		appVersionDto.ios = appVersionDao.getIos();
		appVersionDto.regDt = appVersionDao.getRegDt();

		return appVersionDto;
	}
}
