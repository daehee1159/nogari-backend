package com.msm.nogari.core.dao.board.review;

import com.msm.nogari.core.dto.board.review.ReviewCommentDto;
import lombok.Getter;

/**
 * @author 최대희
 * @since 2024-05-09
 */
@Getter
public class ReviewCommentDao {
	private Long commentSeq;

	private Long boardSeq;
	private Long memberSeq;
	private String nickname;

	private String content;

	private String deleteYN;

	private String modDt;
	private String regDt;

	public static ReviewCommentDao of(ReviewCommentDto commentDto) {
		ReviewCommentDao commentDao = new ReviewCommentDao();

		commentDao.commentSeq = commentDto.getCommentSeq();

		commentDao.boardSeq = commentDto.getBoardSeq();
		commentDao.memberSeq = commentDto.getMemberSeq();
		commentDao.nickname = commentDto.getNickname();

		commentDao.content = commentDto.getContent();

		commentDao.deleteYN = commentDto.getDeleteYN();

		commentDao.modDt = commentDto.getModDt();
		commentDao.regDt = commentDto.getRegDt();

		return commentDao;
	}
}
