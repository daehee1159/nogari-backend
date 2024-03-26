package com.msm.nogari.core.jwt.service;

import com.msm.nogari.core.dao.MemberDao;
import com.msm.nogari.core.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author 최대희
 * @since 2023-11-28
 */
@Service
@RequiredArgsConstructor
public class JwtService implements UserDetailsService {

	private final MemberMapper memberMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// id, pw 검증 로직
		MemberDao memberDao = memberMapper.getMemberInfoByUsername(username);

		return User.builder()
			.username(memberDao.getUsername())
			.password(memberDao.getPassword())
			.roles(memberDao.getUserRole())
			.build();
	}
}
