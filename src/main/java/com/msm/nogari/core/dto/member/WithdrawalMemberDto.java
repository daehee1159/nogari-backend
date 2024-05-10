package com.msm.nogari.core.dto.member;

import com.msm.nogari.core.dao.member.WithdrawalMemberDao;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 최대희
 * @since 2023-11-29
 */
@Getter
@Setter
public class WithdrawalMemberDto {
	private Long memberSeq;

	private String reasonMessage;

	public static WithdrawalMemberDto of(WithdrawalMemberDao withdrawalMemberDao) {
		WithdrawalMemberDto withdrawalMemberDto = new WithdrawalMemberDto();

		withdrawalMemberDto.memberSeq = withdrawalMemberDao.getMemberSeq();

		withdrawalMemberDto.reasonMessage = withdrawalMemberDao.getReasonMessage();

		return withdrawalMemberDto;
	}
}
