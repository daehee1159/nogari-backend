package com.msm.nogari.core.mapper;

import com.msm.nogari.core.dao.board.community.BoardDao;
import com.msm.nogari.core.dao.board.community.BoardLikeDao;
import com.msm.nogari.core.dao.board.community.ChildCommentDao;
import com.msm.nogari.core.dao.board.community.CommentDao;
import com.msm.nogari.core.dto.board.community.BoardDto;
import com.msm.nogari.core.dto.board.community.BoardLikeDto;
import com.msm.nogari.core.dto.board.community.ChildCommentDto;
import com.msm.nogari.core.dto.board.community.CommentDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 최대희
 * @since 2023-11-29
 */
@Mapper
public interface CommunityMapper {
	boolean setCommunity(BoardDao boardDao);

	List<BoardDao> getAllCommunity();

	List<BoardDao> getLikeCommunity(int likeCount);

	List<BoardDao> getNoticeCommunity();

	List<BoardDao> searchBoard(Map<String, Object> map);

	int getCntOfComment(Long boardSeq);
	BoardDao getCommunityByIdx(Long boardSeq);

//	List<BoardDto> getCommunity(@Param("memberSeq") Long memberSeq, @Param("p") int p, @Param("size") int size,  @Param("endRow") Long endRow);
//	List<BoardDto> getCommunity(@Param("memberSeq") Long memberSeq, @Param("p") int p, @Param("size") int size);

	boolean updateCommunity(BoardDao boardDao);
	List<BoardDao> getCommunity(Long memberSeq);

	boolean deleteCommunity(Long boardSeq);
	boolean addBoardViewCnt(Long boardSeq);

	List<BoardLikeDao> getBoardLikeCnt(Long boardSeq);
	boolean setBoardLike(BoardLikeDao boardLikeDto);
	boolean deleteBoardLike(BoardLikeDao boardLikeDto);

	// 댓글
	boolean setComment(CommentDao commentDao);

	List<CommentDao> getComment(Long boardSeq);
	boolean deleteComment(Long commentSeq);

	// 대댓글
	List<ChildCommentDao> getChildComment(@Param("boardSeq") Long boardSeq, @Param("commentSeq") Long commentSeq);
	Long setChildComment(ChildCommentDao childCommentDao);
	boolean deleteChildComment(Long childCommentSeq);
}
