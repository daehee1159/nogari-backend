package com.msm.nogari.core.dto.board.community;

import com.msm.nogari.core.dao.board.community.ChildCommentDao;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 최대희
 * @since 2023-12-03
 */
@Getter
@Setter
public class ChildCommentDto {
	private Long childCommentSeq;

	private Long boardSeq;
	private Long commentSeq;
	private Long memberSeq;
	private String nickname;

	private String content;

	private String deleteYN;

	private String modDt;
	private String regDt;

	public static ChildCommentDto of(ChildCommentDao childCommentDao) {
		ChildCommentDto childCommentDto = new ChildCommentDto();

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
