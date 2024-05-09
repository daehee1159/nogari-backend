package com.msm.nogari.core.dao.member;

import com.msm.nogari.core.dto.member.WithdrawalMemberDto;
import lombok.Getter;

/**
 * @author 최대희
 * @since 2024-05-09
 */
@Getter
public class WithdrawalMemberDao {
	private Long memberSeq;

	private String reasonMessage;

	public static WithdrawalMemberDao of(WithdrawalMemberDto withdrawalMemberDto) {
		WithdrawalMemberDao withdrawalMemberDao = new WithdrawalMemberDao();

		withdrawalMemberDao.memberSeq = withdrawalMemberDto.getMemberSeq();

		withdrawalMemberDao.reasonMessage = withdrawalMemberDto.getReasonMessage();

		return withdrawalMemberDao;
	}
}
