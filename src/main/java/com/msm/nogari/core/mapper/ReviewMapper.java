package com.msm.nogari.core.mapper;

import com.msm.nogari.core.dao.board.review.ReviewBoardDao;
import com.msm.nogari.core.dao.board.review.ReviewBoardLikeDao;
import com.msm.nogari.core.dao.board.review.ReviewChildCommentDao;
import com.msm.nogari.core.dao.board.review.ReviewCommentDao;
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
	boolean setReview(ReviewBoardDao reviewBoardDao);

	List<ReviewBoardDao> getAllReview();

	List<ReviewBoardDao> getLikeReview(int likeCount);

	List<ReviewBoardDao> getNoticeReview();

	List<ReviewBoardDao> searchReviewBoard(Map<String, Object> map);

	int getCntOfComment(Long boardSeq);
	ReviewBoardDao getReviewByIdx(Long boardSeq);

	boolean updateReview(ReviewBoardDao boardDao);
	List<ReviewBoardDao> getReview(Long memberSeq);

	boolean deleteReview(Long boardSeq);
	boolean addBoardViewCnt(Long boardSeq);

	List<ReviewBoardLikeDao> getBoardLikeCnt(Long boardSeq);
	boolean setBoardLike(ReviewBoardLikeDao boardLikeDao);
	boolean deleteBoardLike(ReviewBoardLikeDao boardLikeDao);

	// 댓글
	boolean setComment(ReviewCommentDao reviewCommentDao);

	List<ReviewCommentDao> getComment(Long boardSeq);
	boolean deleteComment(Long commentSeq);

	// 대댓글
	List<ReviewChildCommentDao> getChildComment(@Param("boardSeq") Long boardSeq, @Param("commentSeq") Long commentSeq);
	Long setChildComment(ReviewChildCommentDao childCommentDao);
	boolean deleteChildComment(Long childCommentSeq);
}
