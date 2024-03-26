package com.msm.nogari.api.service;

import com.msm.nogari.core.dao.MemberDao;
import com.msm.nogari.core.dto.member.*;
import com.msm.nogari.core.enums.MemberStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 최대희
 * @since 2023-11-28
 */
public interface MemberService {
	/**
	 * 회원가입
	 */
	Object memberRegistration(MemberDto memberDto, HttpServletRequest httpServletRequest);

	/**
	 * 가입 상태 체크
	 */
	MemberStatus getMemberStatus(MemberDto memberDto);

	/**
	 * email 을 넘기면 memberSeq 를 리턴
	 */
	Long getMemberSeqByEmail(String email);

	/**
	 * email, nickName 중복 체크
	 */
	boolean isDuplicate(String type, String value);

	/**
	 * Get FCM Token
	 */
	String getDeviceToken(Long memberSeq);

	/**
	 * 기기변경 등으로 인한 deviceToken 변경
	 */
	boolean changeDeviceToken(MemberDto memberDto);

	/**
	 * 회원 정보
	 */
	MemberDao getMemberInfo(Long memberSeq);

	/**
	 * 닉네임 수정
	 */
	boolean updateNickName(MemberDto memberDto);

	/**
	 * 회원 복구 (휴면 회원, 탈퇴 회원 복구)
	 */
	boolean restoreMember(MemberDto memberDto);

	/**
	 * 회원 탈퇴
	 */
	boolean withdrawalMember(WithdrawalMemberDto withdrawalMemberDto) throws Exception;

	/**
	 * 회원 차단
	 */
	Long blockMember(BlockDto blockDto);

	/**
	 * 차단 회원 조회
	 */
	List<BlockDto> getBlockMember(Long memberSeq);

	/**
	 * 차단 회원 삭제
	 */
	boolean deleteBlockMember(List<BlockDto> blockDtoList);

	/************ 계급 및 포인트 관련 ************/

	/**
	 * 포인트 지급
	 */
	boolean setPoint(PointHistoryDto pointHistoryDto);

	/**
	 * 현재 포인트 및 계급 조회
	 */
	LevelDto getLevelAndPoint(Long memberSeq);

	/**
	 * 포인트 이력 보기
	 */
	List<PointHistoryDto> getPointHistory(Long memberSeq);
	List<PointHistoryDto> getPointHistoryToday(Long memberSeq);

	/**
	 * 포인트 이력 Set
	 */
	boolean setPointHistory(PointHistoryDto pointHistoryDto);

	/************ 계급 및 포인트 관련 ************/

	/**
	 * Get Notification
	 */
	List<NotificationDto> getNotification(Long memberSeq);

	MemberDao getMemberInfoByUsername(String username);

	/**
	 * 포인트 계산 후 Update
	 */
	boolean updateLevelByPoint(Long memberSeq, int point);
}
