package com.msm.nogari.core.mapper;

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
	boolean setCommunity(BoardDto boardDto);

	List<BoardDto> getAllCommunity();

	List<BoardDto> getLikeCommunity(int likeCount);

	List<BoardDto> getNoticeCommunity();

	List<BoardDto> searchBoard(Map<String, Object> map);

	int getCntOfComment(Long boardSeq);
	BoardDto getCommunityByIdx(Long boardSeq);

//	List<BoardDto> getCommunity(@Param("memberSeq") Long memberSeq, @Param("p") int p, @Param("size") int size,  @Param("endRow") Long endRow);
//	List<BoardDto> getCommunity(@Param("memberSeq") Long memberSeq, @Param("p") int p, @Param("size") int size);

	boolean updateCommunity(BoardDto boardDto);
	List<BoardDto> getCommunity(Long memberSeq);

	boolean deleteCommunity(Long boardSeq);
	boolean addBoardViewCnt(Long boardSeq);

	List<BoardLikeDto> getBoardLikeCnt(Long boardSeq);
	boolean setBoardLike(BoardLikeDto boardLikeDto);
	boolean deleteBoardLike(BoardLikeDto boardLikeDto);

	// 댓글
	boolean setComment(CommentDto commentDto);

	List<CommentDto> getComment(Long boardSeq);
	boolean deleteComment(Long commentSeq);

	// 대댓글
	List<ChildCommentDto> getChildComment(@Param("boardSeq") Long boardSeq, @Param("commentSeq") Long commentSeq);
	Long setChildComment(ChildCommentDto childCommentDto);
	boolean deleteChildComment(Long childCommentSeq);
}
