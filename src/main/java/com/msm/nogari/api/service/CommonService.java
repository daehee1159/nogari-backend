package com.msm.nogari.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.msm.nogari.core.dto.common.AppVersionDto;
import com.msm.nogari.core.dto.common.NewsResponse;
import com.msm.nogari.core.dto.common.ReportDto;
import com.msm.nogari.core.dto.common.TaxInfoDto;

import java.util.List;

/**
 * @author 최대희
 * @since 2024-01-03
 */
public interface CommonService {

	AppVersionDto getAppVersion();
	boolean setHoliday(int year) throws Exception;
	String extractOGImage(String newsUrl);

	boolean setNews(String keyword) throws JsonProcessingException;

	List<NewsResponse.NewsItem> getNews();

	boolean setIncomeTaxExcel();

	TaxInfoDto getTaxInfo(String standardDt);

	boolean reportBoard(ReportDto reportDto);
}
