package com.msm.nogari.api.controller;

import com.msm.nogari.api.service.ManHourService;
import com.msm.nogari.core.dto.man_hour.ManHourDto;
import com.msm.nogari.core.dto.man_hour.ManHourHistoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 최대희
 * @since 2023-12-11
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/man-hour", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ManHourController {

	private final ManHourService manHourService;

	/**
	 * 공수 캘린더 등록
	 * 1개씩, 여러개씩 등록할 수 있어서 List로 받음
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	public boolean setManHour(@RequestBody List<ManHourDto> manHourDtoList) throws Exception {
		return manHourService.setManHour(manHourDtoList);
	}

	/**
	 * 공수 캘린더 불러오기
	 */
	@RequestMapping(value = "/{memberSeq}", method = RequestMethod.GET)
	public List<ManHourDto> getManHourList(@PathVariable Long memberSeq) {
		return manHourService.getManHourList(memberSeq);
	}

	/**
	 * 공수 캘린더 수정하기
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public boolean updateManHour(@RequestBody ManHourDto manHourDto) {
		return manHourService.updateManHour(manHourDto);
	}

	/**
	 * 공수 캘린더 삭제하기
	 * 일별 삭제, 월별 삭제 기능 때문에 parameter를 List로 받음
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public boolean deleteManHour(@RequestBody List<ManHourDto> manHourDtoList) {
		return manHourService.deleteManHour(manHourDtoList);
	}

	/**
	 * 공수 캘린더 이력 보기
	 * startDt, endDt 기간별로 조회하게 만듬
	 */
	@RequestMapping(value = "/history/{memberSeq}", method = RequestMethod.GET)
	public List<ManHourHistoryDto> getManHourHistory(@PathVariable Long memberSeq, @RequestParam("startDt") String startDt, @RequestParam("endDt") String endDt) {
		return manHourService.getManHourHistory(memberSeq, startDt, endDt);
	}



}
