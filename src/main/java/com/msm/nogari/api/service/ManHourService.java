package com.msm.nogari.api.service;

import com.msm.nogari.core.dto.man_hour.ManHourDto;
import com.msm.nogari.core.dto.man_hour.ManHourHistoryDto;

import java.util.List;

/**
 * @author 최대희
 * @since 2023-12-11
 */
public interface ManHourService {
	/**
	 * 공수 캘린더 등록
	 */
	boolean setManHour(List<ManHourDto> manHourDtoList) throws Exception;

	/**
	 * 공수 캘린더 불러오기
	 */
	List<ManHourDto> getManHourList(Long memberSeq);

	/**
	 * 공수 캘린더 수정하기
	 */
	boolean updateManHour(ManHourDto manHourDto);

	/**
	 * 공수 캘린더 삭제하기
	 */
	boolean deleteManHour(List<ManHourDto> manHourDtoList);

	/**
	 * 공수 캘린더 이력 보기
	 * startDt, endDt 기간별로 조회하게 만듬
	 */
	List<ManHourHistoryDto> getManHourHistory(Long memberSeq, String startDt, String endDt);
}
