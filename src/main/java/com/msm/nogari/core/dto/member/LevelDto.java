package com.msm.nogari.core.dto.member;

import com.msm.nogari.core.dao.member.LevelDao;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 최대희
 * @since 2024-01-22
 */
@Getter
@Setter
public class LevelDto {
	private Long levelSeq;

	private Long memberSeq;
	private int level;
	private int point;

	private String modDt;
	private String regDt;

	public static LevelDto of(LevelDao levelDao) {
		LevelDto levelDto = new LevelDto();

		levelDto.levelSeq = levelDao.getLevelSeq();

		levelDto.memberSeq = levelDao.getMemberSeq();
		levelDto.level = levelDao.getLevel();
		levelDto.point = levelDao.getPoint();

		levelDto.modDt = levelDao.getModDt();
		levelDto.regDt = levelDao.getRegDt();

		return levelDto;
	}
}
