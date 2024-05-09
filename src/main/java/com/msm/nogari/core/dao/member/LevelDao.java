package com.msm.nogari.core.dao.member;

import com.msm.nogari.core.dto.member.LevelDto;
import lombok.Getter;

/**
 * @author 최대희
 * @since 2024-05-09
 */
@Getter
public class LevelDao {
	private Long levelSeq;

	private Long memberSeq;
	private int level;
	private int point;

	private String modDt;
	private String regDt;

	public static LevelDao of(LevelDto levelDto) {
		LevelDao levelDao = new LevelDao();

		levelDao.levelSeq = levelDto.getLevelSeq();

		levelDao.memberSeq = levelDto.getMemberSeq();
		levelDao.level = levelDto.getLevel();
		levelDao.point = levelDto.getPoint();

		levelDao.modDt = levelDto.getModDt();
		levelDao.regDt = levelDto.getRegDt();

		return levelDao;
	}
}
