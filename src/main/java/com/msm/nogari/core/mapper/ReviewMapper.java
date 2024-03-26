package com.msm.nogari.core.mapper;

import com.msm.nogari.core.dto.board.review.ReviewBoardDto;
import com.msm.nogari.core.dto.board.review.ReviewBoardLikeDto;
import com.msm.nogari.core.dto.board.review.ReviewChildCommentDto;
import com.msm.nogari.core.dto.board.review.ReviewCommentDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 최대희
 * @since 2023-11-29
 */
@Mapper
public interface ReviewMapper {
	boolean setReview(ReviewBoardDto reviewBoardDto);

	List<ReviewBoardDto> getAllReview();

	List<ReviewBoardDto> getLikeReview(int likeCount);

	List<ReviewBoardDto> getNoticeReview();

	List<ReviewBoardDto> searchReviewBoard(Map<String, Object> map);

	int getCntOfComment(Long boardSeq);
	ReviewBoardDto getReviewByIdx(Long boardSeq);

	boolean updateReview(ReviewBoardDto boardDto);
	List<ReviewBoardDto> getReview(Long memberSeq);

	boolean deleteReview(Long boardSeq);
	boolean addBoardViewCnt(Long boardSeq);

	List<ReviewBoardLikeDto> getBoardLikeCnt(Long boardSeq);
	boolean setBoardLike(ReviewBoardLikeDto boardLikeDto);
	boolean deleteBoardLike(ReviewBoardLikeDto boardLikeDto);

	// 댓글
	boolean setComment(ReviewCommentDto reviewCommentDto);

	List<ReviewCommentDto> getComment(Long boardSeq);
	boolean deleteComment(Long commentSeq);

	// 대댓글
	List<ReviewChildCommentDto> getChildComment(@Param("boardSeq") Long boardSeq, @Param("commentSeq") Long commentSeq);
	Long setChildComment(ReviewChildCommentDto childCommentDto);
	boolean deleteChildComment(Long childCommentSeq);
}
