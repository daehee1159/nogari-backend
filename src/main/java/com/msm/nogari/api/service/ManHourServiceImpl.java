package com.msm.nogari.api.service;

import com.msm.nogari.core.dao.man_hour.ManHourDao;
import com.msm.nogari.core.dto.man_hour.ManHourDto;
import com.msm.nogari.core.dto.man_hour.ManHourHistoryDto;
import com.msm.nogari.core.mapper.ManHourMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 최대희
 * @since 2023-12-11
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ManHourServiceImpl implements ManHourService {
	private final ManHourMapper manHourMapper;

	@Override
	@Transactional
	public boolean setManHour(List<ManHourDto> manHourDtoList) throws Exception {
		try {
			// for문으로 하나씩 저장
			for (ManHourDto manHourDto : manHourDtoList) {
				manHourMapper.setManHour(ManHourDao.of(manHourDto));
			}
			return true;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public List<ManHourDto> getManHourList(Long memberSeq) {
		return manHourMapper.getManHourList(memberSeq).stream()
			.map(ManHourDto::of)
			.collect(Collectors.toList());
	}

	@Override
	public boolean updateManHour(ManHourDto manHourDto) {
		return manHourMapper.updateManHour(ManHourDao.of(manHourDto));
	}

	@Override
	public boolean deleteManHour(List<ManHourDto> manHourDtoList) {
		List<Long> manHourSeqList = new ArrayList<>();

		for (ManHourDto manHourDto : manHourDtoList) {
			manHourSeqList.add(manHourDto.getManHourSeq());
		}

		return manHourMapper.deleteManHour(manHourSeqList);
	}

	@Override
	public List<ManHourDto> getManHourHistory(Long memberSeq, String startDt, String endDt) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		LocalDateTime start = LocalDateTime.parse(startDt);
		LocalDateTime end = LocalDateTime.parse(endDt);

		return manHourMapper.getManHourHistory(memberSeq, start, end).stream()
			.map(ManHourDto::of)
			.collect(Collectors.toList());
	}
}
