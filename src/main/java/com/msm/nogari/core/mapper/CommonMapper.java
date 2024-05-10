package com.msm.nogari.core.mapper;

import com.msm.nogari.core.dao.common.AppVersionDao;
import com.msm.nogari.core.dao.common.ReportDao;
import com.msm.nogari.core.dao.man_hour.ManHourDao;
import com.msm.nogari.core.dto.common.AppVersionDto;
import com.msm.nogari.core.dto.common.NewsResponse;
import com.msm.nogari.core.dto.common.ReportDto;
import com.msm.nogari.core.dto.common.TaxInfoDto;
import com.msm.nogari.core.dto.man_hour.ManHourDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 최대희
 * @since 2024-01-03
 */
@Mapper
public interface CommonMapper {
	AppVersionDao getAppVersion();
	List<ManHourDao> getAllHolidays();
	boolean setHoliday(ManHourDao manHourDao);

	boolean setNews(List<NewsResponse.NewsItem> newsItemList);
	List<NewsResponse.NewsItem> getNews();

	TaxInfoDto getTaxInfo(String standardDt);

	boolean reportBoard(ReportDao reportDao);

}
