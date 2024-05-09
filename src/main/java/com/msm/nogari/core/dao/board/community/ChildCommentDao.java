package com.msm.nogari.core.dao.board.community;

import com.msm.nogari.core.dto.board.community.ChildCommentDto;
import lombok.Getter;

/**
 * @author 최대희
 * @since 2024-05-09
 */
@Getter
public class ChildCommentDao {
	private Long childCommentSeq;

	private Long boardSeq;
	private Long commentSeq;
	private Long memberSeq;
	private String nickname;

	private String content;

	private String deleteYN;

	private String modDt;
	private String regDt;

	public static ChildCommentDao of(ChildCommentDto childCommentDto) {
		ChildCommentDao childCommentDao = new ChildCommentDao();

		childCommentDao.childCommentSeq = childCommentDto.getChildCommentSeq();

		childCommentDao.boardSeq = childCommentDto.getBoardSeq();
		childCommentDao.commentSeq = childCommentDto.getCommentSeq();
		childCommentDao.memberSeq = childCommentDto.getMemberSeq();
		childCommentDao.nickname = childCommentDto.getNickname();

		childCommentDao.content = childCommentDto.getContent();

		childCommentDao.deleteYN = childCommentDto.getDeleteYN();

		childCommentDao.modDt = childCommentDto.getModDt();
		childCommentDao.regDt = childCommentDto.getRegDt();

		return childCommentDao;
	}
}
