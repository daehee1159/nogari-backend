package com.msm.nogari.core.dto.member;

import com.msm.nogari.core.dao.member.MemberDao;
import com.msm.nogari.core.enums.MemberStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author 최대희
 * @since 2023-11-28
 */
@Getter
@Setter
public class MemberDto {
	private Long memberSeq;

	private String email;
	private String nickName;

	// iOS, Android
	private String device;
	// iOS 기기의 경우 apple login 시 email 을 공개하지 않는 경우 identifier 를 제공
	private String identifier;
	private String deviceToken;
	private MemberStatus status;
	private String userRole;

	private LocalDateTime modDt;
	private LocalDateTime regDt;

	private String ipAddr;

	public static MemberDto of(MemberDao memberDao) {
		MemberDto memberDto = new MemberDto();

		memberDto.memberSeq = memberDao.getMemberSeq();

		memberDto.email = memberDao.getEmail();
		memberDto.nickName = memberDao.getNickName();

		memberDto.device = memberDao.getDevice();
		memberDto.identifier = memberDao.getIdentifier();
		memberDto.deviceToken = memberDao.getDeviceToken();
		memberDto.status = memberDao.getStatus();
		memberDto.userRole = memberDao.getUserRole();

		memberDto.modDt = memberDao.getModDt();
		memberDto.regDt = memberDao.getRegDt();

		memberDto.ipAddr = memberDao.getIpAddr();

		return memberDto;
	}
}
