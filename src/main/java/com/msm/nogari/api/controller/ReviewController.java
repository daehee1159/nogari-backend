package com.msm.nogari.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.msm.nogari.api.service.ReviewService;
import com.msm.nogari.core.dto.board.review.ReviewBoardDto;
import com.msm.nogari.core.dto.board.review.ReviewBoardLikeDto;
import com.msm.nogari.core.dto.board.review.ReviewChildCommentDto;
import com.msm.nogari.core.dto.board.review.ReviewCommentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 최대희
 * @since 2023-11-29
 * 게시글 및 댓글 작성 후 포인트 지급이 기본
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/review", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ReviewController {
	private final ReviewService reviewService;

	/**
	 * 게시글 작성
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	public boolean setReview(@RequestBody ReviewBoardDto reviewBoardDto) {
		return reviewService.setReview(reviewBoardDto);
	}

	/**
	 * 페이징 처리 후 전체 게시글 조회
	 * p = 페이지 번호, size = 페이지 사이즈 (페이지당 몇개씩), navigatePages = 하단 페이지 번호 몇개씩 뿌려줄 것인가
	 */
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public PageInfo<ReviewBoardDto> getPage(@RequestParam("p") int p, @RequestParam("size") int size) {
		PageHelper.startPage(p,size);
		return PageInfo.of(reviewService.getAllReview(), 5);
	}

	@RequestMapping(value = "/like/page", method = RequestMethod.GET)
	public PageInfo<ReviewBoardDto> getLikePage(@RequestParam("p") int p, @RequestParam("size") int size, @RequestParam("likeCount") int likeCount) {
		PageHelper.startPage(p,size);
		return PageInfo.of(reviewService.getLikeReview(likeCount), 5);
	}

	@RequestMapping(value = "/notice/page", method = RequestMethod.GET)
	public PageInfo<ReviewBoardDto> getNoticePage(@RequestParam("p") int p, @RequestParam("size") int size) {
		PageHelper.startPage(p,size);
		return PageInfo.of(reviewService.getNoticeCommunity(), 5);
	}

	/**
	 * 리뷰 검색 기능
	 * 검색 조건
	 * 1.제목
	 * 2.제목+내용
	 * 3.내용
	 * 4.글쓴이(닉네임)
	 * 5.댓글
	 * 위 조건들로 검색 시 페이징 처리 후 리턴
	 * @RequestParam(value = "", required = false) 하면 필수값 아님
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public PageInfo<ReviewBoardDto> searchBoard(@RequestParam("searchCondition") String searchCondition, @RequestParam("keyword") String keyword, @RequestParam("p") int p, @RequestParam("size") int s) {
		// mapper 에서 if 문을 통해 검색중
		PageHelper.startPage(p,s);
		return PageInfo.of(reviewService.searchBoard(searchCondition, keyword));
	}

	@RequestMapping(value = "/like/search", method = RequestMethod.GET)
	public PageInfo<ReviewBoardDto> searchLikeBoard(@RequestParam("searchCondition") String searchCondition, @RequestParam("keyword") String keyword, @RequestParam("p") int p, @RequestParam("size") int s, @RequestParam("likeCount") int likeCount) {
		// mapper 에서 if 문을 통해 검색중
		PageHelper.startPage(p,s);
		return PageInfo.of(reviewService.searchLikeBoard(searchCondition, keyword, likeCount));
	}

	@RequestMapping(value = "/notice/search", method = RequestMethod.GET)
	public PageInfo<ReviewBoardDto> searchNoticeBoard(@RequestParam("searchCondition") String searchCondition, @RequestParam("keyword") String keyword, @RequestParam("p") int p, @RequestParam("size") int s) {
		// mapper 에서 if 문을 통해 검색중
		PageHelper.startPage(p,s);
		return PageInfo.of(reviewService.searchNoticeBoard(searchCondition, keyword));
	}

	/**
	 * 댓글 갯수 조회
	 */
	@RequestMapping(value = "/comment/cnt", method = RequestMethod.GET)
	public Map<Long, Integer> getCntOfComment(@RequestParam List<Long> boardSeqList) {
		return reviewService.getCntOfComment(boardSeqList);
	}

	/**
	 * 1개 게시글 조회
	 */
	@RequestMapping(value = "/{boardSeq}", method = RequestMethod.GET)
	public ReviewBoardDto getReviewBySeq(@PathVariable Long boardSeq) {
		return reviewService.getReviewBySeq(boardSeq);
	}

	/**
	 * 내가 쓴 게시글 조회
	 */
	@RequestMapping(value = "/my/{memberSeq}", method = RequestMethod.GET)
	public PageInfo<ReviewBoardDto> getMyReview(@PathVariable Long memberSeq, @RequestParam("p") int p, @RequestParam("size") int size) {
		PageHelper.startPage(p,size);
		return PageInfo.of(reviewService.getMyReview(memberSeq));
	}

	/**
	 * 게시글 삭제
	 */
	@RequestMapping(value = "/{boardSeq}", method = RequestMethod.DELETE)
	public boolean deleteReview(@PathVariable Long boardSeq) {
		return reviewService.deleteReview(boardSeq);
	}

	/**
	 * 게시글 viewCount++
	 */
	@RequestMapping(value = "/view/{boardSeq}", method = RequestMethod.GET)
	public boolean addBoardViewCnt(@PathVariable Long boardSeq) {
		return reviewService.addBoardViewCnt(boardSeq);
	}

	/**
	 * board_like 테이블 조회
	 */
	@RequestMapping(value = "/like/{boardSeq}", method = RequestMethod.GET)
	public List<ReviewBoardLikeDto> getBoardLikeCnt(@PathVariable Long boardSeq) {
		return reviewService.getBoardLikeCnt(boardSeq);
	}

	/**
	 * 게시글 좋아요 누르기 & 취소 누르기 (좋아요 눌러진 상태에서 한번 더 누를 시 취소)
	 * 이걸 뭘로 판단..?
	 */
	@RequestMapping(value = "/like", method = RequestMethod.POST)
	public boolean setBoardLike(@RequestBody ReviewBoardLikeDto reviewBoardLikeDto) {
		return reviewService.setBoardLike(reviewBoardLikeDto);
	}

	@RequestMapping(value = "/like", method = RequestMethod.DELETE)
	public boolean deleteBoardLike(@RequestBody ReviewBoardLikeDto reviewBoardLikeDto) {
		return reviewService.deleteBoardLike(reviewBoardLikeDto);
	}

	/**
	 * 댓글 작성
	 */
	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	public Long setComment(@RequestBody ReviewCommentDto reviewCommentDto) {
		return reviewService.setComment(reviewCommentDto);
	}

	/**
	 * 댓글 조회
	 */
	@RequestMapping(value = "/comment/{boardSeq}", method = RequestMethod.GET)
	public List<ReviewCommentDto> getComment(@PathVariable Long boardSeq) {
		return reviewService.getComment(boardSeq);
	}

	/**
	 * 댓글 수정
	 */
	@RequestMapping(value = "/comment/update", method = RequestMethod.POST)
	public boolean updateComment(@RequestBody ReviewCommentDto reviewCommentDto) {
		return reviewService.updateComment(reviewCommentDto);
	}

	/**
	 * 댓글 삭제
	 */
	@RequestMapping(value = "/comment/{commentSeq}", method = RequestMethod.DELETE)
	public boolean deleteComment(@PathVariable Long commentSeq) {
		return reviewService.deleteComment(commentSeq);
	}

	/**
	 * 대댓글 작성
	 */
	@RequestMapping(value = "/child/comment", method = RequestMethod.POST)
	public Long setChildComment(@RequestBody ReviewChildCommentDto reviewChildCommentDto) {
		return reviewService.setChildComment(reviewChildCommentDto);
	}

	/**
	 * 대댓글 조회
	 */
	@RequestMapping(value = "/child/comment/{boardSeq}/{commentSeq}", method = RequestMethod.GET)
	public List<ReviewChildCommentDto> getChildComment(@PathVariable Long boardSeq, @PathVariable Long commentSeq) {
		return reviewService.getChildComment(boardSeq, commentSeq);
	}

	/**
	 * 대댓글 수정
	 */
	@RequestMapping(value = "/child/comment/update", method = RequestMethod.POST)
	public boolean updateChildComment(@RequestBody ReviewChildCommentDto reviewChildCommentDto) {
		return reviewService.updateChildComment(reviewChildCommentDto);
	}

	/**
	 * 대댓글 삭제
	 */
	@RequestMapping(value = "/child/comment/{childCommentSeq}", method = RequestMethod.DELETE)
	public boolean deleteChildComment(@PathVariable Long childCommentSeq) {
		return reviewService.deleteChildComment(childCommentSeq);
	}
}
