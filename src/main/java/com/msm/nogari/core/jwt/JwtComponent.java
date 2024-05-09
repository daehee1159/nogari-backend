package com.msm.nogari.core.jwt;

import com.msm.nogari.core.dao.member.MemberDao;
import com.msm.nogari.core.jwt.enums.CommonException;
import com.msm.nogari.core.jwt.enums.EnumSecurityException;
import com.msm.nogari.core.jwt.service.JwtService;
import com.msm.nogari.core.mapper.MemberMapper;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author 최대희
 * @since 2023-11-28
 */
@Component
public class JwtComponent {

	private final String ACCESS_SECRET_KEY;
	private final String REFRESH_SECRET_KEY;
	private final long ACCESS_EXPIRE_MINUTES;
	private final long REFRESH_EXPIRE_MINUTES;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private MemberMapper memberMapper;


	/**
	 *	토큰 초기값 세팅
	 * @param aCCESS_SECRET_KEY
	 * @param REFRESH_SECRET_KEY
	 * @param aCCESS_EXPIRE_MINUTES
	 * @param rEFRESH_EXPIRE_MINUTES
	 */
	public JwtComponent(
		@Value("${jwt.access.token.secure.key}") String aCCESS_SECRET_KEY,
		@Value("${jwt.refresh.token.secure.key}") String REFRESH_SECRET_KEY,
		@Value("${jwt.access.token.expire.time}") long aCCESS_EXPIRE_MINUTES,
		@Value("${jwt.refresh.token.expire.time}") long rEFRESH_EXPIRE_MINUTES) {

		super();
		this.ACCESS_SECRET_KEY = aCCESS_SECRET_KEY;
		this.REFRESH_SECRET_KEY = REFRESH_SECRET_KEY;
		this.ACCESS_EXPIRE_MINUTES = aCCESS_EXPIRE_MINUTES;
		this.REFRESH_EXPIRE_MINUTES = rEFRESH_EXPIRE_MINUTES;
	}

	// 토큰타입
	public enum TOKEN_TYPE{
		ACCESS_TOKEN, REFRESH_TOKEN
	}

	// 토큰 타입 데이터
	private class TokenTypeData{

		private final String key;
		private final long time;

		public TokenTypeData(String key, long time) {
			super();
			this.key = key;
			this.time = time;
		}

		public String getKey() {
			return key;
		}

		public long getTime() {
			return time;
		}
	}


	/**
	 * 만료시간 추출
	 *
	 * @param token
	 * @param tokenType
	 * @return
	 * @throws CommonException
	 */
	private Date extractExpiration(String token, TOKEN_TYPE tokenType) throws CommonException {

		return this.extractClaim(token, Claims::getExpiration, tokenType);
	}

	/**
	 * 토큰 파싱
	 * @param token
	 * @return
	 * @throws CommonException
	 */
	private Claims extractAllClaims(String token, TokenTypeData ttd) throws CommonException {

		Claims body = null;
		try {

			body = Jwts.parser()
				.setSigningKey( ttd.getKey())
				.parseClaimsJws(token)
				.getBody();

		} catch (ExpiredJwtException e) {

			throw new CommonException(e, EnumSecurityException.ExpiredJwtException);
		} catch (UnsupportedJwtException e) {
			System.out.println("AccessToken 시간 만료");
			throw new CommonException(e, EnumSecurityException.UnsupportedJwtException);
		} catch (MalformedJwtException e) {

			throw new CommonException(e, EnumSecurityException.MalformedJwtException);
		} catch (SignatureException e) {

			throw new CommonException(e, EnumSecurityException.SignatureException);
		} catch (IllegalArgumentException e) {

			throw new CommonException(e, EnumSecurityException.IllegalArgumentException);
		}

		return body;
	}

	/**
	 * 토큰 만료 확인
	 *
	 * @param token
	 * @return
	 * @throws CommonException
	 */
	private Boolean isTokenExpired( String token,  TOKEN_TYPE tokenType) throws CommonException {

		Date date = this.extractExpiration( token, tokenType);
		return date.before( new Date());
	}

	/**
	 *
	 * @param tokenType
	 * @return
	 */
	private TokenTypeData makeTokenTypeData( TOKEN_TYPE tokenType) {

		String key = tokenType==TOKEN_TYPE.ACCESS_TOKEN?this.ACCESS_SECRET_KEY:this.REFRESH_SECRET_KEY;
		long time = tokenType==TOKEN_TYPE.ACCESS_TOKEN?this.ACCESS_EXPIRE_MINUTES:this.REFRESH_EXPIRE_MINUTES;
		return new TokenTypeData(key, time);
	}
	/**
	 * 토큰 생성
	 *
	 * @param claims
	 * @param subject
	 * @return
	 */
	private String createToken(Map<String, Object> claims, String subject, TOKEN_TYPE tokenType) {

		TokenTypeData ttd = this.makeTokenTypeData(tokenType);

		LocalDateTime d = LocalDateTime.now().plusMinutes( ttd.getTime());

		return Jwts.builder()
			.setClaims(claims)
			.setSubject( subject)
			.setExpiration( Date.from(d.atZone(ZoneId.systemDefault()).toInstant()))
			.setIssuedAt( Date.from( LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
			.signWith( SignatureAlgorithm.HS256, ttd.getKey())
			.compact();
	}

	/**
	 * 토큰 생성
	 * @param userDetails
	 * @return
	 */
	private JwtDto generateToken( UserDetails userDetails) {

		Map<String, Object> claims = new HashMap<String, Object>();
		String accessToken = this.createToken(claims, userDetails.getUsername(), TOKEN_TYPE.ACCESS_TOKEN);
		String refreshToken = this.createToken(claims, userDetails.getUsername(), TOKEN_TYPE.REFRESH_TOKEN);

		MemberDao memberDao = memberMapper.getMemberInfoByUsername(userDetails.getUsername());

		return new JwtDto(accessToken, refreshToken, userDetails.getUsername(), userDetails.getPassword());
	}

	/**
	 * 토큰에서 사용자 이름 추출
	 *
	 * @param token
	 * @return
	 * @throws CommonException
	 */
	public String extractUsername( String token, TOKEN_TYPE tokenType) throws CommonException {

		String subject = this.extractClaim(token, Claims::getSubject, tokenType);
		return subject;
	}

	/**
	 *
	 * @param <T>
	 * @param token
	 * @param claimsResolver
	 * @param tokenType
	 * @return
	 * @throws CommonException
	 */
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver, TOKEN_TYPE tokenType) throws CommonException {

		TokenTypeData ttd = this.makeTokenTypeData(tokenType);

		final Claims claims = extractAllClaims( token , ttd);;
		return claimsResolver.apply( claims);
	}


	/**
	 * OAUTH
	 * @param username
	 * @param password
	 * @return
	 * @throws CommonException
	 */
	public JwtDto makeJwt(String username, String password) throws Exception {

		try {
			this.authenticationManager
				.authenticate(
					new UsernamePasswordAuthenticationToken(
						username,
						password));

			final UserDetails user = jwtService.loadUserByUsername(username);
			final JwtDto jwtDto = this.generateToken(user);

			return jwtDto;
		} catch ( BadCredentialsException e) {

			System.out.println(username);
			System.out.println(password);
			throw new CommonException(e, EnumSecurityException.BadCredentialsException);
		}

	}

	/**
	 * 재발행
	 * @param
	 * @return
	 * @throws CommonException
	 */
	public JwtDto makeReJwt() throws CommonException {

		try {

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			final UserDetails user = jwtService.loadUserByUsername( authentication.getName());
			final JwtDto jwtDto = this.generateToken(user);

			return jwtDto;
		} catch ( BadCredentialsException e) {

			throw new CommonException(e, EnumSecurityException.BadCredentialsException);
		}

	}

	/**
	 * 토큰 검증
	 *
	 * @param token
	 * @param userDetails
	 * @return
	 * @throws CommonException
	 */
	public Boolean validateToken( String token, UserDetails userDetails, TOKEN_TYPE tokenType) throws CommonException {

		final String username = this.extractUsername(token, tokenType);
		return ( username.equals( userDetails.getUsername()) && !isTokenExpired(token, tokenType));
	}
}
