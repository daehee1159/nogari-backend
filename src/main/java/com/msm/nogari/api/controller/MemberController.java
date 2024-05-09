package com.msm.nogari.api.controller;

import com.msm.nogari.api.service.MemberService;
import com.msm.nogari.core.dao.member.MemberDao;
import com.msm.nogari.core.dto.member.*;
import com.msm.nogari.core.enums.MemberStatus;
import com.msm.nogari.core.jwt.JwtComponent;
import com.msm.nogari.core.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 최대희
 * @since 2023-11-28
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/member", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;

	private final JwtComponent jwtComponent;
	private final JwtService jwtService;

	@Value("/get_access_token")
	private String accessTokenUrl;

	/**
	 * 회원가입
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Object memberRegistration(@RequestBody MemberDto memberDto, HttpServletRequest httpServletRequest) {
		return memberService.memberRegistration(memberDto, httpServletRequest);
	}

	/**
	 * kakao login 인가코드 redirect
	 */
	@RequestMapping(value = "/oauth", method = RequestMethod.GET)
	public Object getKakaoCode(@RequestParam(value = "code", required = false) String code) {
		System.out.println("kakao code :" + code);
		return true;
	}

	/**
	 * 가입 상태 체크
	 */
	@RequestMapping(value = "/status", method = RequestMethod.POST)
	public MemberStatus getMemberStatus(@RequestBody MemberDto memberDto) {
		return memberService.getMemberStatus(memberDto);
	}

	/**
	 * email 을 넘기면 memberSeq 를 리턴
	 */
	@RequestMapping(value = "/email", method = RequestMethod.POST)
	public Long getMemberSeqByEmail(@RequestBody MemberDto memberDto) {
		return memberService.getMemberSeqByEmail(memberDto.getEmail());
	}

	/**
	 * email, nickName 중복 체크
	 */
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	public boolean isDuplicate(@RequestParam(value = "type") String type, @RequestParam(value = "value") String value) {
		return memberService.isDuplicate(type, value);
	}

	/**
	 * Get Device Token
	 */
	@RequestMapping(value = "/check/token/{memberSeq}", method = RequestMethod.GET)
	public String getDeviceToken(@PathVariable Long memberSeq) {
		return memberService.getDeviceToken(memberSeq);
	}

	/**
	 * 기기변경 등으로 인한 deviceToken 변경
	 */
	@RequestMapping(value = "/device", method = RequestMethod.PATCH)
	public boolean changeDeviceToken(@RequestBody MemberDto memberDto) {
		return memberService.changeDeviceToken(memberDto);
	}

	/**
	 * 회원 정보
	 */
	@RequestMapping(value = "/info/{memberSeq}", method = RequestMethod.GET)
	public MemberDao getMemberInfo(@PathVariable Long memberSeq) {
		return memberService.getMemberInfo(memberSeq);
	}

	/**
	 * 닉네임 수정
	 */
	@RequestMapping(value = "/nickname", method = RequestMethod.POST)
	public boolean updateNickName(@RequestBody MemberDto memberDto) {
		return memberService.updateNickName(memberDto);
	}

	/**
	 * 회원 복구 (휴면 회원, 탈퇴 회원 복구)
	 */
	@RequestMapping(value = "/restore", method = RequestMethod.POST)
	public boolean restoreMember(@RequestBody MemberDto memberDto) {
		return memberService.restoreMember(memberDto);
	}

	/**
	 * 회원 탈퇴
	 */
	@RequestMapping(value = "", method = RequestMethod.DELETE)
	public boolean withdrawalMember(@RequestBody WithdrawalMemberDto withdrawalMemberDto) throws Exception {
		return memberService.withdrawalMember(withdrawalMemberDto);
	}

	/**
	 * 회원 차단
	 */
	@RequestMapping(value = "/block", method = RequestMethod.POST)
	public Long blockMember(@RequestBody BlockDto blockDto) {
		return memberService.blockMember(blockDto);
	}

	/**
	 * 차단 회원 조회
	 */
	@RequestMapping(value = "/block/{memberSeq}", method = RequestMethod.GET)
	public List<BlockDto> getBlockMember(@PathVariable Long memberSeq) {
		return memberService.getBlockMember(memberSeq);
	}

	/**
	 * 차단 회원 삭제
	 */
	@RequestMapping(value = "/block", method = RequestMethod.DELETE)
	public boolean deleteBlockMember(@RequestBody List<BlockDto> blockDtoList) {
		return memberService.deleteBlockMember(blockDtoList);
	}


	/************ 계급 및 포인트 관련 ************/

	/**
	 * 포인트 지급
	 * PointHistory, LevelAndPoint, Notification 3개의 set 및 update 진행
	 */
	@RequestMapping(value = "/point", method = RequestMethod.POST)
	public boolean setPoint(@RequestBody PointHistoryDto pointHistoryDto) {
		return memberService.setPoint(pointHistoryDto);
	}

	/**
	 * 현재 포인트 및 계급 조회
	 */
	@RequestMapping(value = "/point/{memberSeq}", method = RequestMethod.GET)
	public LevelDto getLevelAndPoint(@PathVariable Long memberSeq) {
		return memberService.getLevelAndPoint(memberSeq);
	}

	/**
	 * 포인트 이력 보기
	 */
	@RequestMapping(value = "/point/history/{memberSeq}", method = RequestMethod.GET)
	public List<PointHistoryDto> getPointHistory(@PathVariable Long memberSeq) {
		return memberService.getPointHistory(memberSeq);
	}

	/**
	 * 오늘 포인트 이력 보기
	 * Flutter 에서 Provider 로 관리하기 위해 SplashScreen 에서 호출
	 */
	@RequestMapping(value = "/point/history/today/{memberSeq}", method = RequestMethod.GET)
	public List<PointHistoryDto> getPointHistoryToday(@PathVariable Long memberSeq) {
		return memberService.getPointHistoryToday(memberSeq);
	}

	/************ 계급 및 포인트 관련 ************/


	/**
	 * Get Notification
	 */
	@RequestMapping(value = "/notification/{memberSeq}", method = RequestMethod.GET)
	public List<NotificationDto> getNotification(@PathVariable Long memberSeq) {
		return memberService.getNotification(memberSeq);
	}

	/**
	 * Set Level And Point
	 */
	@RequestMapping(value = "/level", method = RequestMethod.POST)
	public boolean setLevelByPoint(@RequestBody LevelDto levelDto) {
		return memberService.updateLevelByPoint(levelDto.getMemberSeq(), levelDto.getPoint());
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String getTest(HttpServletRequest httpServletRequest) {
		String ip = httpServletRequest.getRemoteAddr();
		return ip;
	}

}
