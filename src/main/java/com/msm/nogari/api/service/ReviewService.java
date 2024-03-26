package com.msm.nogari.api.service;

import com.msm.nogari.core.dto.board.review.ReviewBoardDto;
import com.msm.nogari.core.dto.board.review.ReviewBoardLikeDto;
import com.msm.nogari.core.dto.board.review.ReviewChildCommentDto;
import com.msm.nogari.core.dto.board.review.ReviewCommentDto;

import java.util.List;
import java.util.Map;

/**
 * @author 최대희
 * @since 2023-11-29
 */
public interface ReviewService {

	/**
	 * 게시글 작성
	 */
	boolean setReview(ReviewBoardDto reviewBoardDto);

	/**
	 * 페이징 처리 후 전체 게시글 조회
	 */
	List<ReviewBoardDto> getAllReview();
	List<ReviewBoardDto> getLikeReview(int likeCount);
	List<ReviewBoardDto> getNoticeCommunity();

	/**
	 * 리뷰 검색 기능
	 */
	List<ReviewBoardDto> searchBoard(String searchCondition, Object keyword);
	List<ReviewBoardDto> searchLikeBoard(String searchCondition, Object keyword, int likeCount);
	List<ReviewBoardDto> searchNoticeBoard(String searchCondition, Object keyword);

	/**
	 * 댓글 갯수 조회
	 */
	Map<Long, Integer> getCntOfComment(List<Long> boardSeqList);

	/**
	 * 1개 게시글 조회
	 */
	ReviewBoardDto getReviewBySeq(Long boardSeq);

	/**
	 * 내가 쓴 게시글 조회
	 */
	List<ReviewBoardDto> getMyReview(Long memberSeq);

	/**
	 * 게시글 삭제
	 */
	boolean deleteReview(Long boardSeq);

	/**
	 * 게시글 viewCount++
	 */
	boolean addBoardViewCnt(Long boardSeq);

	/**
	 * board_like 테이블 조회
	 */
	List<ReviewBoardLikeDto> getBoardLikeCnt(Long boardSeq);

	/**
	 * 게시글 좋아요 누르기 & 취소 누르기 (좋아요 눌러진 상태에서 한번 더 누를 시 취소)
	 */
	boolean setBoardLike(ReviewBoardLikeDto reviewBoardLikeDto);
	boolean deleteBoardLike(ReviewBoardLikeDto reviewBoardLikeDto);

	/**
	 * 댓글 작성
	 */
	Long setComment(ReviewCommentDto reviewCommentDto);

	/**
	 * 댓글 조회
	 */
	List<ReviewCommentDto> getComment(Long boardSeq);

	/**
	 * 댓글 수정
	 */
	boolean updateComment(ReviewCommentDto reviewCommentDto);

	/**
	 * 댓글 삭제
	 */
	boolean deleteComment(Long commentSeq);

	/**
	 * 대댓글 작성
	 */
	Long setChildComment(ReviewChildCommentDto reviewChildCommentDto);

	/**
	 * 대댓글 조회
	 */
	List<ReviewChildCommentDto> getChildComment(Long boardSeq, Long commentSeq);

	/**
	 * 대댓글 수정
	 */
	boolean updateChildComment(ReviewChildCommentDto reviewChildCommentDto);

	/**
	 * 대댓글 삭제
	 */
	boolean deleteChildComment(Long childCommentSeq);
}
