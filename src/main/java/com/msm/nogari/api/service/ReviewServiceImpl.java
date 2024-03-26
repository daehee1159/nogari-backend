package com.msm.nogari.api.service;

import com.msm.nogari.core.dto.board.review.ReviewBoardDto;
import com.msm.nogari.core.dto.board.review.ReviewBoardLikeDto;
import com.msm.nogari.core.dto.board.review.ReviewChildCommentDto;
import com.msm.nogari.core.dto.board.review.ReviewCommentDto;
import com.msm.nogari.core.dto.member.NotificationDto;
import com.msm.nogari.core.dto.member.PointHistoryDto;
import com.msm.nogari.core.enums.NotificationType;
import com.msm.nogari.core.enums.PointHistory;
import com.msm.nogari.core.mapper.MemberMapper;
import com.msm.nogari.core.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author 최대희
 * @since 2023-11-29
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
	private final ReviewMapper reviewMapper;
	private final MemberMapper memberMapper;
	private final MemberService memberService;

	@Override
	public boolean setReview(ReviewBoardDto reviewBoardDto) {
		boolean result = reviewMapper.setReview(reviewBoardDto);

		List<PointHistoryDto> pointHistoryDtoList = memberMapper.getPointHistoryToday(reviewBoardDto.getMemberSeq());
		int reviewWritingCnt = 0;

		for (int i = 0; i < pointHistoryDtoList.size(); i++) {
			if (pointHistoryDtoList.get(i).getPointHistory() == PointHistory.REVIEW_WRITING) {
				reviewWritingCnt++;
			}
		}

		PointHistoryDto resultHistoryDto;
		PointHistoryDto pointHistoryDto = new PointHistoryDto();
		// PointHistory Set
		pointHistoryDto.setMemberSeq(reviewBoardDto.getMemberSeq());
		pointHistoryDto.setPointHistory(PointHistory.REVIEW_WRITING);
		pointHistoryDto.setHistoryComment(PointHistory.REVIEW_WRITING.getText());
		pointHistoryDto.setBoardSeq(reviewBoardDto.getBoardSeq());
		if (reviewWritingCnt >= 3) {
			pointHistoryDto.setPoint(0);
		} else {
			pointHistoryDto.setPoint(PointHistory.REVIEW_WRITING.getPoint());
		}

		resultHistoryDto = PointHistory.getResultComment(pointHistoryDto);

		memberMapper.setPointHistory(resultHistoryDto);

		// Level and Point Set
		memberService.updateLevelByPoint(reviewBoardDto.getMemberSeq(), pointHistoryDto.getPoint());

		// Notification Set
		NotificationDto notificationDto = new NotificationDto();
		notificationDto.setMemberSeq(reviewBoardDto.getMemberSeq());
		notificationDto.setType(NotificationType.REVIEW_BOARD);
		notificationDto.setMessage(resultHistoryDto.getResultComment());
		notificationDto.setBoardSeq(resultHistoryDto.getBoardSeq());
		memberMapper.setNotification(notificationDto);

		return result;
	}

	@Override
	public List<ReviewBoardDto> getAllReview() {
		return reviewMapper.getAllReview();
	}

	@Override
	public List<ReviewBoardDto> getLikeReview(int likeCount) {
		return reviewMapper.getLikeReview(likeCount);
	}

	@Override
	public List<ReviewBoardDto> getNoticeCommunity() {
		return reviewMapper.getNoticeReview();
	}

	@Override
	public List<ReviewBoardDto> searchBoard(String searchCondition, Object keyword) {
		Map<String, Object> map = new HashMap<>();
		map.put("searchCondition", searchCondition);
		if (Objects.equals(searchCondition, "memberSeq")) {
			map.put("keyword", Integer.parseInt((String) keyword));
		} else {
			map.put("keyword", keyword);
		}
		return reviewMapper.searchReviewBoard(map);
	}

	@Override
	public List<ReviewBoardDto> searchLikeBoard(String searchCondition, Object keyword, int likeCount) {
		return null;
	}

	@Override
	public List<ReviewBoardDto> searchNoticeBoard(String searchCondition, Object keyword) {
		return null;
	}

	@Override
	public Map<Long, Integer> getCntOfComment(List<Long> boardSeqList) {
		Map<Long, Integer> resultMap = new HashMap<>();
		for (Long aLong : boardSeqList) {
			int cnt = reviewMapper.getCntOfComment(aLong);
			resultMap.put(aLong, cnt);
		}
		return resultMap;
	}

	@Override
	public ReviewBoardDto getReviewBySeq(Long boardSeq) {
		return reviewMapper.getReviewByIdx(boardSeq);
	}

	@Override
	public List<ReviewBoardDto> getMyReview(Long memberSeq) {
		return reviewMapper.getReview(memberSeq);
	}

	@Override
	public boolean deleteReview(Long boardSeq) {
		return reviewMapper.deleteReview(boardSeq);
	}

	@Override
	public boolean addBoardViewCnt(Long boardSeq) {
		return reviewMapper.addBoardViewCnt(boardSeq);
	}

	@Override
	public List<ReviewBoardLikeDto> getBoardLikeCnt(Long boardSeq) {
		return reviewMapper.getBoardLikeCnt(boardSeq);
	}

	@Override
	public boolean setBoardLike(ReviewBoardLikeDto reviewBoardLikeDto) {
		return reviewMapper.setBoardLike(reviewBoardLikeDto);
	}

	@Override
	public boolean deleteBoardLike(ReviewBoardLikeDto reviewBoardLikeDto) {
		return reviewMapper.deleteBoardLike(reviewBoardLikeDto);
	}

	@Override
	public Long setComment(ReviewCommentDto reviewCommentDto) {
		boolean result = reviewMapper.setComment(reviewCommentDto);

		List<PointHistoryDto> pointHistoryDtoList = memberMapper.getPointHistoryToday(reviewCommentDto.getMemberSeq());
		int reviewCommentCnt = 0;

		for (int i = 0; i < pointHistoryDtoList.size(); i++) {
			if (pointHistoryDtoList.get(i).getPointHistory() == PointHistory.REVIEW_COMMENT) {
				reviewCommentCnt++;
			}
		}

		PointHistoryDto resultHistoryDto;
		PointHistoryDto pointHistoryDto = new PointHistoryDto();
		// PointHistory Set
		pointHistoryDto.setMemberSeq(reviewCommentDto.getMemberSeq());
		pointHistoryDto.setPointHistory(PointHistory.REVIEW_COMMENT);
		pointHistoryDto.setHistoryComment(PointHistory.REVIEW_COMMENT.getText());
		pointHistoryDto.setBoardSeq(reviewCommentDto.getCommentSeq());
		if (reviewCommentCnt >= 3) {
			pointHistoryDto.setPoint(0);
		} else {
			pointHistoryDto.setPoint(PointHistory.REVIEW_COMMENT.getPoint());
		}

		resultHistoryDto = PointHistory.getResultComment(pointHistoryDto);

		memberMapper.setPointHistory(resultHistoryDto);

		// Level and Point Set
		memberService.updateLevelByPoint(reviewCommentDto.getMemberSeq(), pointHistoryDto.getPoint());

		// Notification Set
		NotificationDto notificationDto = new NotificationDto();
		notificationDto.setMemberSeq(reviewCommentDto.getMemberSeq());
		notificationDto.setType(NotificationType.REVIEW_COMMENT);
		notificationDto.setMessage(resultHistoryDto.getResultComment());
		notificationDto.setBoardSeq(resultHistoryDto.getBoardSeq());
		memberMapper.setNotification(notificationDto);

		if (reviewCommentDto.getCommentSeq() != null) {
			return reviewCommentDto.getCommentSeq();
		} else {
			return 0L;
		}
	}

	@Override
	public List<ReviewCommentDto> getComment(Long boardSeq) {
		return reviewMapper.getComment(boardSeq);
	}

	@Override
	public boolean updateComment(ReviewCommentDto reviewCommentDto) {
		return false;
	}

	@Override
	public boolean deleteComment(Long commentSeq) {
		return reviewMapper.deleteComment(commentSeq);
	}

	@Override
	public Long setChildComment(ReviewChildCommentDto reviewChildCommentDto) {
		return reviewMapper.setChildComment(reviewChildCommentDto);
	}

	@Override
	public List<ReviewChildCommentDto> getChildComment(Long boardSeq, Long commentSeq) {
		return reviewMapper.getChildComment(boardSeq, commentSeq);
	}

	@Override
	public boolean updateChildComment(ReviewChildCommentDto reviewChildCommentDto) {
		return false;
	}

	@Override
	public boolean deleteChildComment(Long childCommentSeq) {
		return reviewMapper.deleteChildComment(childCommentSeq);
	}
}
