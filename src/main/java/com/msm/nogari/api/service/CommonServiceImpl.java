package com.msm.nogari.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msm.nogari.NogariApplication;
import com.msm.nogari.core.dto.common.AppVersionDto;
import com.msm.nogari.core.dto.common.NewsResponse;
import com.msm.nogari.core.dto.common.ReportDto;
import com.msm.nogari.core.dto.common.TaxInfoDto;
import com.msm.nogari.core.dto.man_hour.ManHourDto;
import com.msm.nogari.core.mapper.CommonMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author 최대희
 * @since 2024-01-03
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CommonServiceImpl implements CommonService {
	private final CommonMapper commonMapper;
	private final ObjectMapper objectMapper;

	@Override
	public AppVersionDto getAppVersion() {
		return commonMapper.getAppVersion();
	}

	@Override
	@Transactional
	public boolean setHoliday(String jsonData) throws Exception {
		try {
			// JSON 문자열을 JsonNode 로 변환
			JsonNode jsonNode = objectMapper.readTree(jsonData);
			// 필요한 부분만 추출하여 DTO 매핑
			JsonNode bodyNode = jsonNode.get("body");

			if (bodyNode != null) {
				JsonNode itemsNode = bodyNode.get("items");
				if ( itemsNode != null && itemsNode.has("item") && itemsNode.get("item").isArray()) {
					List<ManHourDto> manHourDtoList = new ArrayList<>();
					for (JsonNode item : itemsNode.get("item")) {
						// 각각의 item 을 DTO 로 매핑
						ManHourDto manHourDto = new ManHourDto();
						manHourDto.setMemo(item.get("dateName").asText());
						manHourDto.setIsHoliday((Objects.equals(item.get("isHoliday").asText(), "Y")) ? "Y" : "N");
						manHourDto.setStartDt(convertLocalDateTime(item.get("locdate").asText()).toString());
						manHourDto.setEndDt(convertLocalDateTime(item.get("locdate").asText()).toString());
						manHourDto.setManHour(0);
						manHourDto.setTotalAmount(0);
						manHourDto.setUnitPrice(0);
						manHourDto.setMemberSeq(0L);

						manHourDtoList.add(manHourDto);
					}

					List<ManHourDto> allHolidayList = commonMapper.getAllHolidays();

					if (!allHolidayList.isEmpty()) {
						// 공휴일 정보를 담은 리스트에서 기존에 등록된 공휴일은 재등록하지 말아야하기 때문에 필터링
						manHourDtoList.removeIf(manHourDto ->
							allHolidayList.stream()
								.anyMatch(allHoliday ->
									allHoliday.getStartDt().toString().equals(manHourDto.getStartDt().toString())
										&& allHoliday.getMemo().equals(manHourDto.getMemo()))
						);
					}
					return setHolidayList(manHourDtoList);
				} else {
					throw new Exception("bodyNode is null!!");
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			throw new Exception("Failed to json pasing : " + e.getMessage());
		}
	}

	@Override
	public String extractOGImage(String newsUrl) {
		try {
			// 해당 URL에서 HTML을 가져옴
			Document document = Jsoup.connect(newsUrl).get();

			// og:image 태그를 찾음
			Elements ogImageElements = document.select("meta[property=og:image]");

			// og:image 태그가 존재하는 경우, content 속성 값을 반환
			if (!ogImageElements.isEmpty()) {
				Element ogImageElement = ogImageElements.first();
				return ogImageElement.attr("content");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		// 기본값이나 오류 처리를 반환할 수 있음
		return null;
	}

	@Override
	public boolean setNews(String keyword) throws JsonProcessingException {
		String clientId = "clientId";
		String clientSecret = "clientSecret";

		String text;

		// 요청 헤더 설정
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Naver-Client-Id", clientId);
		headers.set("X-Naver-Client-Secret", clientSecret);

		try {
			keyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("검색어 인코딩 실패");
		}

		String apiURL = "https://openapi.naver.com/v1/search/news.json?query=" + keyword;

		Map<String, String> requestHeaders = new HashMap<>();
		requestHeaders.put("X-Naver-Client-Id", clientId);
		requestHeaders.put("X-Naver-Client-Secret", clientSecret);
		String responseBody = get(apiURL, requestHeaders);

		ObjectMapper objectMapper = new ObjectMapper();
		NewsResponse newsResponse = objectMapper.readValue(responseBody, NewsResponse.class);

		for (int i = 0; i < newsResponse.getItems().size(); i++) {
			String ogImage = extractOGImage(newsResponse.getItems().get(i).getLink());
			if (ogImage != null) {
				newsResponse.getItems().get(i).setImgUrl(ogImage);
			} else {
				newsResponse.getItems().get(i).setImgUrl("");
			}
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
			LocalDateTime pubDateTime = LocalDateTime.parse(newsResponse.getItems().get(i).getPubDate(), formatter);

			newsResponse.getItems().get(i).setPubDate(pubDateTime.toString());
		}

		return commonMapper.setNews(newsResponse.getItems());
	}

	@Override
	public List<NewsResponse.NewsItem> getNews() {
		return commonMapper.getNews();
	}

	@Override
	public boolean setIncomeTaxExcel() {
		try {
			// MySQL 연결 설정
			String url = "jdbc:mysql://localhost:3306/nogari_tax";
			String user = "root";
			String password = "root";
			Connection connection = DriverManager.getConnection(url, user, password);

			// 엑셀 파일 읽기
			InputStream file = NogariApplication.class.getClassLoader().getResourceAsStream("common/incometax.xlsx");
			Workbook workbook = WorkbookFactory.create(file);
			Sheet sheet = workbook.getSheetAt(0); // 첫 번째 시트를 선택

			// MySQL에 데이터 삽입
			String sql = "INSERT INTO nogari_tax.incometax (more, under, amount, regDt) VALUES (?, ?, ?, now())";
			PreparedStatement statement = connection.prepareStatement(sql);

			for (Row row : sheet) {
				Cell cell1 = row.getCell(0);
				Cell cell2 = row.getCell(1);
				Cell cell3 = row.getCell(2);
				String value1 = String.valueOf((int) cell1.getNumericCellValue());
				String value2 = String.valueOf((int) cell2.getNumericCellValue());
				String value3 = String.valueOf((int) cell3.getNumericCellValue());
				// ... 나머지 필드 설정
				// SQL 쿼리의 각 ?에 대해 값을 설정
				statement.setString(1, value1);
				statement.setString(2, value2);
				statement.setString(3, value3);

				statement.executeUpdate();
			}

			// 자원 해제
			statement.close();
			connection.close();
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public TaxInfoDto getTaxInfo(String standardDt) {
		return commonMapper.getTaxInfo(standardDt);
	}

	@Override
	public boolean reportBoard(ReportDto reportDto) {
		return commonMapper.reportBoard(reportDto);
	}

	private static LocalDateTime convertLocalDateTime(String dateString) {
		LocalDate localDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyyMMdd"));
		return LocalDateTime.of(localDate, LocalTime.MIN);
	}

	@Transactional
	public boolean setHolidayList(List<ManHourDto> manHourDtoList) {
		try {
			for (ManHourDto manHourDto : manHourDtoList) {
				commonMapper.setHoliday(manHourDto);
			}
			return true;
		} catch (Exception e) {
			throw new RuntimeException("Failed to save setHoliday: " + e.getMessage());
		}
	}

	private static String get(String apiUrl, Map<String, String> requestHeaders) {
		HttpURLConnection con = connect(apiUrl);
		try {
			con.setRequestMethod("GET");
			for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
				con.setRequestProperty(header.getKey(), header.getValue());
			}


			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
				return readBody(con.getInputStream());
			} else { // 오류 발생
				return readBody(con.getErrorStream());
			}
		} catch (IOException e) {
			throw new RuntimeException("API 요청과 응답 실패", e);
		} finally {
			con.disconnect();
		}
	}

	private static HttpURLConnection connect(String apiUrl){
		try {
			URL url = new URL(apiUrl);
			return (HttpURLConnection)url.openConnection();
		} catch (MalformedURLException e) {
			throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
		} catch (IOException e) {
			throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
		}
	}


	private static String readBody(InputStream body){
		InputStreamReader streamReader = new InputStreamReader(body);


		try (BufferedReader lineReader = new BufferedReader(streamReader)) {
			StringBuilder responseBody = new StringBuilder();


			String line;
			while ((line = lineReader.readLine()) != null) {
				responseBody.append(line);
			}


			return responseBody.toString();
		} catch (IOException e) {
			throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
		}
	}
}
