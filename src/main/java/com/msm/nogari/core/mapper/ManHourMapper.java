package com.msm.nogari.core.mapper;

import com.msm.nogari.core.dto.man_hour.ManHourDto;
import com.msm.nogari.core.dto.man_hour.ManHourHistoryDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 최대희
 * @since 2023-12-11
 */
@Mapper
public interface ManHourMapper {
	boolean setManHour(ManHourDto manHourDto);

	List<ManHourDto> getManHourList(Long memberSeq);

	ManHourDto getManHour(Long manHourSeq);

	boolean updateManHour(ManHourDto manHourDto);

	boolean deleteManHour(@Param("manHourSeqList") List<Long> manHourSeqList);

	List<ManHourHistoryDto> getManHourHistory(@Param("memberSeq") Long memberSeq, @Param("startDt") LocalDateTime startDt, @Param("endDt") LocalDateTime endDt);
}
