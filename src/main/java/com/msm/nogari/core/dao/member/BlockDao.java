package com.msm.nogari.core.dao.member;

import com.msm.nogari.core.dto.member.BlockDto;
import lombok.Getter;

/**
 * @author 최대희
 * @since 2024-05-09
 */
@Getter
public class BlockDao {
	private Long blockSeq;
	private Long memberSeq;
	private Long blockMemberSeq;
	private String blockMemberNickname;
	private String regDt;

	public static BlockDao of(BlockDto blockDto) {
		BlockDao blockDao = new BlockDao();

		blockDao.blockSeq = blockDto.getBlockSeq();
		blockDao.memberSeq = blockDto.getMemberSeq();
		blockDao.blockMemberSeq = blockDto.getBlockMemberSeq();
		blockDao.blockMemberNickname = blockDto.getBlockMemberNickname();
		blockDao.regDt = blockDto.getRegDt();

		return blockDao;
	}
}
