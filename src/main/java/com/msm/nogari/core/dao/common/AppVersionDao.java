package com.msm.nogari.core.dao.common;

import com.msm.nogari.core.dto.common.AppVersionDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 최대희
 * @since 2024-05-08
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppVersionDao {
	private Long appVersionSeq;
	private String android;
	private String ios;
	private String regDt;

	public static AppVersionDao of(AppVersionDto appVersionDto) {
		AppVersionDao appVersionDao = new AppVersionDao();

		appVersionDao.appVersionSeq = appVersionDto.getAppVersionSeq();
		appVersionDao.android = appVersionDto.getAndroid();
		appVersionDao.ios = appVersionDto.getIos();
		appVersionDao.regDt = appVersionDto.getRegDt();

		return appVersionDao;
	}
}
