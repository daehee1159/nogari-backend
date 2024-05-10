package com.msm.nogari.core.mapper;

import com.msm.nogari.core.dao.man_hour.ManHourDao;
import com.msm.nogari.core.dao.man_hour.ManHourHistoryDao;
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
	boolean setManHour(ManHourDao manHourDao);

	List<ManHourDao> getManHourList(Long memberSeq);

	ManHourDao getManHour(Long manHourSeq);

	boolean updateManHour(ManHourDao manHourDao);

	boolean deleteManHour(@Param("manHourSeqList") List<Long> manHourSeqList);

	List<ManHourDao> getManHourHistory(@Param("memberSeq") Long memberSeq, @Param("startDt") LocalDateTime startDt, @Param("endDt") LocalDateTime endDt);
}
