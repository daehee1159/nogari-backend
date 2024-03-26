package com.msm.nogari.api.service;

import com.msm.nogari.core.dto.board.community.BoardDto;
import com.msm.nogari.core.dto.board.community.BoardLikeDto;
import com.msm.nogari.core.dto.board.community.ChildCommentDto;
import com.msm.nogari.core.dto.board.community.CommentDto;

import java.util.List;
import java.util.Map;

/**
 * @author 최대희
 * @since 2023-11-29
 */
public interface CommunityService {
	/**
	 * 게시글 작성
	 */
	boolean setCommunity(BoardDto boardDto);

	/**
	 * 전체 게시글 조회
	 */
	List<BoardDto> getAllCommunity();

	/**
	 * 추천 게시글 조회
	 */
	List<BoardDto> getLikeCommunity(int likeCount);

	/**
	 * 공지 게시글 조회
	 */
	List<BoardDto> getNoticeCommunity();

	/**
	 * 커뮤니티 검색 기능
	 */
	List<BoardDto> searchBoard(String searchCondition, Object keyword);
	List<BoardDto> searchLikeBoard(String searchCondition, Object keyword, int likeCount);
	List<BoardDto> searchNoticeBoard(String searchCondition, Object keyword);

	/**
	 * 댓글 갯수 조회
	 */
	Map<Long, Integer> getCntOfComment(List<Long> boardSeqList);

	/**
	 * 1개 게시글 조회
	 */
	BoardDto getCommunityByIdx(Long boardSeq);

	/**
	 * 내가 쓴 게시글 조회
	 */
	List<BoardDto> getCommunity(Long memberSeq);

	/**
	 * 게시글 수정
	 */
	boolean updateCommunity(BoardDto boardDto);

	/**
	 * 게시글 삭제
	 */
	boolean deleteCommunity(Long boardSeq);

	/**
	 * 게시글 viewCount++
	 */
	boolean addBoardViewCnt(Long boardSeq);

	/**
	 * board_like 테이블 조회
	 */
	List<BoardLikeDto> getBoardLikeCnt(Long boardSeq);

	/**
	 * 게시글 좋아요 누르기 & 취소 누르기 (좋아요 눌러진 상태에서 한번 더 누를 시 취소)
	 */
	boolean setBoardLike(BoardLikeDto boardLikeDto);
	boolean deleteBoardLike(BoardLikeDto boardLikeDto);

	/**
	 * 댓글 작성
	 */
	Long setComment(CommentDto commentDto);

	/**
	 * 댓글 조회
	 */
	List<CommentDto> getComment(Long boardSeq);

	/**
	 * 댓글 수정
	 */
	boolean updateComment(CommentDto commentDto);

	/**
	 * 댓글 삭제
	 */
	boolean deleteComment(Long commentSeq);

	/**
	 * 대댓글 작성
	 */
	Long setChildComment(ChildCommentDto childCommentDto);

	/**
	 * 대댓글 조회
	 */
	List<ChildCommentDto> getChildComment(Long boardSeq, Long commentSeq);

	/**
	 * 대댓글 수정
	 */
	boolean updateChildComment(ChildCommentDto childCommentDto);

	/**
	 * 대댓글 삭제
	 */
	boolean deleteChildComment(Long childCommentSeq);

}
