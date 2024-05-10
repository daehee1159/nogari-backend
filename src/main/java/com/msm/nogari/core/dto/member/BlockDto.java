package com.msm.nogari.core.dto.member;

import com.msm.nogari.core.dao.member.BlockDao;
import lombok.Getter;

/**
 * @author 최대희
 * @since 2024-03-12
 */
@Getter
public class BlockDto {
	private Long blockSeq;
	private Long memberSeq;
	private Long blockMemberSeq;
	private String blockMemberNickname;
	private String regDt;

	public static BlockDto of(BlockDao blockDao) {
		BlockDto blockDto = new BlockDto();

		blockDto.blockSeq = blockDao.getBlockSeq();
		blockDto.memberSeq = blockDao.getMemberSeq();
		blockDto.blockMemberSeq = blockDao.getBlockMemberSeq();
		blockDto.blockMemberNickname = blockDao.getBlockMemberNickname();
		blockDto.regDt = blockDao.getRegDt();

		return blockDto;
	}
}
