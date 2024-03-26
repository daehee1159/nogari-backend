package com.msm.nogari.core.dto.member;

import com.msm.nogari.core.enums.Rank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author 최대희
 * @since 2023-11-29
 * 회원가입할 때 Rank 도 같이 insert 해야함
 */
@Getter
@Setter
public class RankDto {
	private Long rankSeq;

	private Long memberSeq;
	private Rank rank;
	private int point;

	private LocalDateTime modDt;
	private LocalDateTime regDt;
}
