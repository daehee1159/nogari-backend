package com.msm.nogari.core.dao.member;

import com.msm.nogari.core.dto.member.MemberDto;
import com.msm.nogari.core.enums.MemberStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author 최대희
 * @since 2023-11-28
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDao implements UserDetails {
	private static final long serialVersionUID = 1L;

	private Long memberSeq;

	private String email;
	private String password;
	private String nickName;

	private String device;
	private String identifier;
	private String deviceToken;
	private MemberStatus status;
	private String userRole;

	private LocalDateTime modDt;
	private LocalDateTime regDt;

	private String ipAddr;

	// UserDetails 기본 상속 변수
	private Collection<? extends GrantedAuthority> authorities;
	private final boolean isEnabled = true;
	private String username;
	private final boolean isCredentialsNonExpired = true;
	private final boolean isAccountNonExpired = true;
	private final boolean isAccountNonLocked = true;

	// static factory method를 사용하여 객체를 캡슐화
	public static MemberDao of(MemberDto memberDto, String encodedPassword) {
		// 유효성 검증
		validate(memberDto, encodedPassword);

		MemberDao memberDao = new MemberDao();

		memberDao.memberSeq = memberDto.getMemberSeq();

		memberDao.email = memberDto.getEmail();
		memberDao.username = memberDto.getEmail();
		memberDao.password = encodedPassword;

		memberDao.nickName = memberDto.getNickName();

		memberDao.device = memberDto.getDevice();
		memberDao.identifier = memberDto.getIdentifier();
		memberDao.deviceToken = memberDto.getDeviceToken();

		if (memberDto.getStatus() == null) {
			memberDao.status = MemberStatus.ACTIVE;
		} else {
			memberDao.status = memberDto.getStatus();
		}

		if (memberDto.getUserRole() == null) {
			memberDao.userRole = "USER";
		} else {
			memberDao.userRole = memberDto.getUserRole();
		}
		memberDao.modDt = memberDto.getModDt();
		memberDao.regDt = LocalDateTime.now();

		memberDao.ipAddr = memberDto.getIpAddr();

		return memberDao;
	}

	private static void validate(MemberDto memberDto, String encodedPassword) {
		if (memberDto.getNickName() == null) {
			throw new NullPointerException("nickName 필드에 값이 없습니다.");
		}

		if (memberDto.getEmail() == null) {
			throw new NullPointerException("Email 필드에 값이 없습니다.");
		}
	}
}
