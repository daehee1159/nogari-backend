package com.msm.nogari.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.msm.nogari.api.service.CommunityService;
import com.msm.nogari.core.dto.board.community.BoardDto;
import com.msm.nogari.core.dto.board.community.BoardLikeDto;
import com.msm.nogari.core.dto.board.community.ChildCommentDto;
import com.msm.nogari.core.dto.board.community.CommentDto;
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
@RequestMapping(value = "/api/community", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CommunityController {
	private final CommunityService communityService;

	/**
	 * 게시글 작성
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	public boolean setCommunity(@RequestBody BoardDto boardDto) {
		return communityService.setCommunity(boardDto);
	}


	/**
	 * 페이징 처리 후 전체 게시글 조회
	 * p = 페이지 번호, size = 페이지 사이즈 (페이지당 몇개씩), navigatePages = 하단 페이지 번호 몇개씩 뿌려줄 것인가
	 */
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public PageInfo<BoardDto> getPage(@RequestParam("p") int p, @RequestParam("size") int size) {
		PageHelper.startPage(p,size);
		return PageInfo.of(communityService.getAllCommunity(), 5);
	}

	@RequestMapping(value = "/like/page", method = RequestMethod.GET)
	public PageInfo<BoardDto> getLikePage(@RequestParam("p") int p, @RequestParam("size") int size, @RequestParam("likeCount") int likeCount) {
		PageHelper.startPage(p,size);
		return PageInfo.of(communityService.getLikeCommunity(likeCount), 5);
	}

	@RequestMapping(value = "/notice/page", method = RequestMethod.GET)
	public PageInfo<BoardDto> getNoticePage(@RequestParam("p") int p, @RequestParam("size") int size) {
		PageHelper.startPage(p,size);
		return PageInfo.of(communityService.getNoticeCommunity(), 5);
	}

	/**
	 * 커뮤니티 검색 기능
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
	public PageInfo<BoardDto> searchBoard(@RequestParam("searchCondition") String searchCondition, @RequestParam("keyword") String keyword, @RequestParam("p") int p, @RequestParam("size") int s) {
		// mapper 에서 if 문을 통해 검색중
		PageHelper.startPage(p,s);
		return PageInfo.of(communityService.searchBoard(searchCondition, keyword));
	}

	@RequestMapping(value = "/like/search", method = RequestMethod.GET)
	public PageInfo<BoardDto> searchLikeBoard(@RequestParam("searchCondition") String searchCondition, @RequestParam("keyword") String keyword, @RequestParam("p") int p, @RequestParam("size") int s, @RequestParam("likeCount") int likeCount) {
		// mapper 에서 if 문을 통해 검색중
		PageHelper.startPage(p,s);
		return PageInfo.of(communityService.searchLikeBoard(searchCondition, keyword, likeCount));
	}

	@RequestMapping(value = "/notice/search", method = RequestMethod.GET)
	public PageInfo<BoardDto> searchNoticeBoard(@RequestParam("searchCondition") String searchCondition, @RequestParam("keyword") String keyword, @RequestParam("p") int p, @RequestParam("size") int s) {
		// mapper 에서 if 문을 통해 검색중
		PageHelper.startPage(p,s);
		return PageInfo.of(communityService.searchNoticeBoard(searchCondition, keyword));
	}

	/**
	 * 댓글 갯수 조회
	 */
	@RequestMapping(value = "/comment/cnt", method = RequestMethod.GET)
	public Map<Long, Integer> getCntOfComment(@RequestParam List<Long> boardSeqList) {
		return communityService.getCntOfComment(boardSeqList);
	}

	/**
	 * 1개 게시글 조회
	 */
	@RequestMapping(value = "/{boardSeq}", method = RequestMethod.GET)
	public BoardDto getCommunityBySeq(@PathVariable Long boardSeq) {
		return communityService.getCommunityByIdx(boardSeq);
	}

	/**
	 * 내가 쓴 게시글 조회
	 */
	@RequestMapping(value = "/my/{memberSeq}", method = RequestMethod.GET)
	public PageInfo<BoardDto> getCommunity(@PathVariable Long memberSeq, @RequestParam("p") int p, @RequestParam("size") int size) {
		PageHelper.startPage(p, size);
		return PageInfo.of(communityService.getCommunity(memberSeq), 5);
	}

	/**
	 * 게시글 수정
	 */
	@RequestMapping(value = "", method = RequestMethod.PATCH)
	public boolean updateCommunity(@RequestBody BoardDto boardDto) {
		return communityService.updateCommunity(boardDto);
	}

	/**
	 * 게시글 삭제
	 */
	@RequestMapping(value = "/{boardSeq}", method = RequestMethod.DELETE)
	public boolean deleteCommunity(@PathVariable Long boardSeq) {
		return communityService.deleteCommunity(boardSeq);
	}

	/**
	 * 게시글 viewCount++
	 */
	@RequestMapping(value = "/view/{boardSeq}", method = RequestMethod.GET)
	public boolean addBoardViewCnt(@PathVariable Long boardSeq) {
		return communityService.addBoardViewCnt(boardSeq);
	}

	/**
	 * board_like 테이블 조회
	 */
	@RequestMapping(value = "/like/{boardSeq}", method = RequestMethod.GET)
	public List<BoardLikeDto> getBoardLikeCnt(@PathVariable Long boardSeq) {
		return communityService.getBoardLikeCnt(boardSeq);
	}

	/**
	 * 게시글 좋아요 누르기 & 취소 누르기 (좋아요 눌러진 상태에서 한번 더 누를 시 취소)
	 * 이걸 뭘로 판단..?
	 */
	@RequestMapping(value = "/like", method = RequestMethod.POST)
	public boolean setBoardLike(@RequestBody BoardLikeDto boardLikeDto) {
		return communityService.setBoardLike(boardLikeDto);
	}

	@RequestMapping(value = "/like", method = RequestMethod.DELETE)
	public boolean deleteBoardLike(@RequestBody BoardLikeDto boardLikeDto) {
		return communityService.deleteBoardLike(boardLikeDto);
	}

	/**
	 * 댓글 작성
	 */
	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	public Long setComment(@RequestBody CommentDto commentDto) {
		return communityService.setComment(commentDto);
	}

	/**
	 * 댓글 조회
	 */
	@RequestMapping(value = "/comment/{boardSeq}", method = RequestMethod.GET)
	public List<CommentDto> getComment(@PathVariable Long boardSeq) {
		return communityService.getComment(boardSeq);
	}

	/**
	 * 댓글 수정
	 */
	@RequestMapping(value = "/comment/update", method = RequestMethod.POST)
	public boolean updateComment(@RequestBody CommentDto commentDto) {
		return communityService.updateComment(commentDto);
	}

	/**
	 * 댓글 삭제
	 */
	@RequestMapping(value = "/comment/{commentSeq}", method = RequestMethod.DELETE)
	public boolean deleteComment(@PathVariable Long commentSeq) {
		return communityService.deleteComment(commentSeq);
	}

	/**
	 * 대댓글 작성
	 */
	@RequestMapping(value = "/child/comment", method = RequestMethod.POST)
	public Long setChildComment(@RequestBody ChildCommentDto childCommentDto) {
		return communityService.setChildComment(childCommentDto);
	}

	/**
	 * 대댓글 조회
	 */
	@RequestMapping(value = "/child/comment/{boardSeq}/{commentSeq}", method = RequestMethod.GET)
	public List<ChildCommentDto> getChildComment(@PathVariable Long boardSeq, @PathVariable Long commentSeq) {
		return communityService.getChildComment(boardSeq, commentSeq);
	}

	/**
	 * 대댓글 수정
	 */
	@RequestMapping(value = "/child/comment/update", method = RequestMethod.POST)
	public boolean updateChildComment(@RequestBody ChildCommentDto childCommentDto) {
		return communityService.updateChildComment(childCommentDto);
	}

	/**
	 * 대댓글 삭제
	 */
	@RequestMapping(value = "/child/comment/{childCommentSeq}", method = RequestMethod.DELETE)
	public boolean deleteChildComment(@PathVariable Long childCommentSeq) {
		return communityService.deleteChildComment(childCommentSeq);
	}

}
