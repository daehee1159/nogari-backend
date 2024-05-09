package com.msm.nogari.core.mapper;

import com.msm.nogari.core.dao.member.MemberDao;
import com.msm.nogari.core.dto.member.*;
import com.msm.nogari.core.enums.MemberStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 최대희
 * @since 2023-11-28
 */
@Mapper
public interface MemberMapper {
	/* 회원가입 */
	boolean memberRegistration(MemberDao memberDao);

	/* username 으로 조회 */
	MemberDao getMemberInfoByUsername(String username);

	MemberDao isDuplicateEmail(String email);
	MemberDao getIosMemberStatus(MemberDto memberDto);

	MemberDao isDuplicateNickName(String nickName);

	String getDeviceToken(Long memberSeq);

	boolean updateDeviceToken(MemberDto memberDto);

	MemberDao getMemberInfo(Long memberSeq);
	boolean updateNickName(MemberDto memberDto);

	boolean withdrawalMember(WithdrawalMemberDto withdrawalMemberDto);

	Long blockMember(BlockDto blockDto);

	List<BlockDto> getBlockMember(Long memberSeq);

	boolean deleteBlockMember(@Param("blockDtoList") List<BlockDto> blockDtoList);
	boolean updateStatus(@Param("memberSeq") Long memberSeq, @Param("status") MemberStatus status);

	boolean setPointHistory(PointHistoryDto pointHistoryDto);
	LevelDto getLevelAndPoint(Long memberSeq);

	List<PointHistoryDto> getPointHistory(Long memberSeq);
	List<PointHistoryDto> getPointHistoryToday(Long memberSeq);

	List<NotificationDto> getNotification(Long memberSeq);

	boolean setNotification(NotificationDto notificationDto);

	LevelDto getLevelData(Long memberSeq);

	boolean setLevelAndPoint(LevelDto levelDto);

	boolean updateLevelAndPoint(LevelDto levelDto);
}
