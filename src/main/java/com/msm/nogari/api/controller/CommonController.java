package com.msm.nogari.api.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.msm.nogari.api.service.CommonService;
import com.msm.nogari.core.dto.common.AppVersionDto;
import com.msm.nogari.core.dto.common.NewsResponse;
import com.msm.nogari.core.dto.common.ReportDto;
import com.msm.nogari.core.dto.common.TaxInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author 최대희
 * @since 2024-01-02
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/common", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CommonController {
	private final CommonService commonService;

	/**
	 * Get App Version
	 */
	@RequestMapping(value = "/app/version", method = RequestMethod.GET)
	public AppVersionDto getAppVersion() {
		return commonService.getAppVersion();
	}

	/**
	 * 공공 데이터 포털 휴일 정보 API
	 */
	@RequestMapping(value = "/holiday", method = RequestMethod.GET)
	public boolean getHolidayData(@RequestParam(value = "solYear") int year) throws Exception {
		String serviceKey = "serviceKey";
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
		String apiUrl = "http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo";
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(apiUrl)
			.queryParam("serviceKey", serviceKey).queryParam("solYear", year)
			.queryParam("numOfRows", 100);
		String responseData = restTemplate.getForObject(uriComponentsBuilder.toUriString(), String.class);

		String jsonData = convertXmlToJson(responseData);

		return commonService.setHoliday(jsonData);
	}

	@RequestMapping(value = "/news", method = RequestMethod.GET)
	public boolean setNews(@RequestParam(value = "keyword") String keyword) throws Exception {
		return commonService.setNews(keyword);
	}

	@RequestMapping(value = "/data/news", method = RequestMethod.GET)
	public List<NewsResponse.NewsItem> getNews() {
		return commonService.getNews();
	}

	@RequestMapping(value = "/income-tax", method = RequestMethod.POST)
	public boolean setIncomeTaxExcel() {
		return commonService.setIncomeTaxExcel();
	}

	@RequestMapping(value = "/tax/{standardDt}", method = RequestMethod.GET)
	public TaxInfoDto getTaxInfo(@PathVariable String standardDt) {
		return commonService.getTaxInfo(standardDt);
	}

	/**
	 * 게시글 신고 기능
	 * 커뮤니티, 리뷰
	 */
	@RequestMapping(value = "/report", method = RequestMethod.POST)
	public boolean reportBoard(@RequestBody ReportDto reportDto) {
		return commonService.reportBoard(reportDto);
	}

	private String convertXmlToJson(String xmlData) {
		try {
			// XmlMapper를 사용하여 XML을 JSON으로 변환
			XmlMapper xmlMapper = new XmlMapper();

			// UTF-8로 인코딩된 바이트 배열로 변환하여 XmlMapper에 전달
			byte[] xmlBytes = xmlData.getBytes(StandardCharsets.UTF_8);
			JsonNode jsonNode = xmlMapper.readTree(xmlBytes);

			// JSON 문자열로 변환
			String jsonData = jsonNode.toString();
			return jsonData;
		} catch (Exception e) {
			// 예외 처리
			e.printStackTrace();
			return null;
		}
	}
}
