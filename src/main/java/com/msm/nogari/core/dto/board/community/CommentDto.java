package com.msm.nogari.core.dto.board.community;

import com.msm.nogari.core.dao.board.community.CommentDao;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 최대희
 * @since 2023-11-28
 */
@Getter
@Setter
public class CommentDto {
	private Long commentSeq;

	private Long boardSeq;
	private Long memberSeq;
	private String nickname;

	private String content;

	private String deleteYN;

	private String modDt;
	private String regDt;

	public static CommentDto of(CommentDao commentDao) {
		CommentDto commentDto = new CommentDto();

		commentDto.commentSeq = commentDao.getCommentSeq();

		commentDto.boardSeq = commentDao.getBoardSeq();
		commentDto.memberSeq = commentDao.getMemberSeq();
		commentDto.nickname = commentDao.getNickname();

		commentDto.content = commentDao.getContent();

		commentDto.deleteYN = commentDao.getDeleteYN();

		commentDto.modDt = commentDao.getModDt();
		commentDto.regDt = commentDao.getRegDt();

		return commentDto;
	}
}
