package com.msm.nogari.core.mapper;

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
	AppVersionDto getAppVersion();
	List<ManHourDto> getAllHolidays();
	boolean setHoliday(ManHourDto manHourDto);

	boolean setNews(List<NewsResponse.NewsItem> newsItemList);
	List<NewsResponse.NewsItem> getNews();

	TaxInfoDto getTaxInfo(String standardDt);

	boolean reportBoard(ReportDto reportDto);

}
