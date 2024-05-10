package com.msm.nogari.core.dto.board.review;

import com.msm.nogari.core.dao.board.review.ReviewChildCommentDao;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 최대희
 * @since 2023-12-03
 */
@Getter
@Setter
public class ReviewChildCommentDto {
	private Long childCommentSeq;

	private Long boardSeq;
	private Long commentSeq;
	private Long memberSeq;
	private String nickname;

	private String content;

	private String deleteYN;

	private String modDt;
	private String regDt;

	public static ReviewChildCommentDto of(ReviewChildCommentDao childCommentDao) {
		ReviewChildCommentDto childCommentDto = new ReviewChildCommentDto();

		childCommentDto.childCommentSeq = childCommentDao.getChildCommentSeq();

		childCommentDto.boardSeq = childCommentDao.getBoardSeq();
		childCommentDto.commentSeq = childCommentDao.getCommentSeq();
		childCommentDto.memberSeq = childCommentDao.getMemberSeq();
		childCommentDto.nickname = childCommentDao.getNickname();

		childCommentDto.content = childCommentDao.getContent();

		childCommentDto.deleteYN = childCommentDao.getDeleteYN();

		childCommentDto.modDt = childCommentDao.getModDt();
		childCommentDto.regDt = childCommentDao.getRegDt();

		return childCommentDto;
	}
}
