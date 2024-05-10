package com.msm.nogari.core.mapper;

import com.msm.nogari.core.dao.member.*;
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
	MemberDao getIosMemberStatus(MemberDao memberDao);

	MemberDao isDuplicateNickName(String nickName);

	String getDeviceToken(Long memberSeq);

	boolean updateDeviceToken(MemberDao memberDao);

	MemberDao getMemberInfo(Long memberSeq);
	boolean updateNickName(MemberDao memberDao);

	boolean withdrawalMember(WithdrawalMemberDao withdrawalMemberDao);

	Long blockMember(BlockDao blockDao);

	List<BlockDao> getBlockMember(Long memberSeq);

	boolean deleteBlockMember(@Param("blockDtoList") List<BlockDao> blockDaoList);
	boolean updateStatus(@Param("memberSeq") Long memberSeq, @Param("status") MemberStatus status);

	boolean setPointHistory(PointHistoryDao pointHistoryDao);
	LevelDto getLevelAndPoint(Long memberSeq);

	List<PointHistoryDao> getPointHistory(Long memberSeq);
	List<PointHistoryDao> getPointHistoryToday(Long memberSeq);

	List<NotificationDao> getNotification(Long memberSeq);

	boolean setNotification(NotificationDao notificationDao);

	LevelDto getLevelData(Long memberSeq);

	boolean setLevelAndPoint(LevelDao levelDao);

	boolean updateLevelAndPoint(LevelDao levelDao);
}
