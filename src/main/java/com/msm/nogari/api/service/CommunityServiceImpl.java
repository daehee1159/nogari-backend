package com.msm.nogari.api.service;

import com.msm.nogari.core.dao.board.community.BoardDao;
import com.msm.nogari.core.dao.board.community.BoardLikeDao;
import com.msm.nogari.core.dao.board.community.ChildCommentDao;
import com.msm.nogari.core.dao.board.community.CommentDao;
import com.msm.nogari.core.dao.member.NotificationDao;
import com.msm.nogari.core.dao.member.PointHistoryDao;
import com.msm.nogari.core.dto.board.community.BoardDto;
import com.msm.nogari.core.dto.board.community.BoardLikeDto;
import com.msm.nogari.core.dto.board.community.ChildCommentDto;
import com.msm.nogari.core.dto.board.community.CommentDto;
import com.msm.nogari.core.dto.member.NotificationDto;
import com.msm.nogari.core.dto.member.PointHistoryDto;
import com.msm.nogari.core.enums.NotificationType;
import com.msm.nogari.core.enums.PointHistory;
import com.msm.nogari.core.mapper.CommunityMapper;
import com.msm.nogari.core.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 최대희
 * @since 2023-11-29
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {
	private final CommunityMapper communityMapper;
	private final MemberMapper memberMapper;
	private final MemberService memberService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean setCommunity(BoardDto boardDto) {
		boolean result = communityMapper.setCommunity(BoardDao.of(boardDto));

		List<PointHistoryDto> pointHistoryDtoList = memberMapper.getPointHistoryToday(boardDto.getMemberSeq()).stream().map(PointHistoryDto::of).collect(Collectors.toList());
		int communityWritingCnt = 0;

		for (int i = 0; i < pointHistoryDtoList.size(); i++) {
			if (pointHistoryDtoList.get(i).getPointHistory() == PointHistory.COMMUNITY_WRITING) {
				communityWritingCnt++;
			}
		}

		PointHistoryDto resultHistoryDto;
		PointHistoryDto pointHistoryDto = new PointHistoryDto();
		// PointHistory Set
		pointHistoryDto.setMemberSeq(boardDto.getMemberSeq());
		pointHistoryDto.setPointHistory(PointHistory.COMMUNITY_WRITING);
		pointHistoryDto.setHistoryComment(PointHistory.COMMUNITY_WRITING.getText());
		pointHistoryDto.setBoardSeq(boardDto.getBoardSeq());
		if (communityWritingCnt >= 3) {
			pointHistoryDto.setPoint(0);
		} else {
			pointHistoryDto.setPoint(PointHistory.COMMUNITY_WRITING.getPoint());
		}

		resultHistoryDto = PointHistory.getResultComment(pointHistoryDto);

		memberMapper.setPointHistory(PointHistoryDao.of(resultHistoryDto));

		// Level and Point Set
		memberService.updateLevelByPoint(boardDto.getMemberSeq(), pointHistoryDto.getPoint());

		// Notification Set
		NotificationDto notificationDto = new NotificationDto();
		notificationDto.setMemberSeq(boardDto.getMemberSeq());
		notificationDto.setType(NotificationType.COMMUNITY_BOARD);
		notificationDto.setMessage(resultHistoryDto.getResultComment());
		notificationDto.setBoardSeq(resultHistoryDto.getBoardSeq());
		memberMapper.setNotification(NotificationDao.of(notificationDto));

		return result;
	}

	@Override
	public List<BoardDto> getAllCommunity() {
		return communityMapper.getAllCommunity().stream()
			.map(BoardDto::of)
			.collect(Collectors.toList());
	}

	@Override
	public List<BoardDto> getLikeCommunity(int likeCount) {
		return communityMapper.getLikeCommunity(likeCount).stream()
			.map(BoardDto::of)
			.collect(Collectors.toList());
	}

	@Override
	public List<BoardDto> getNoticeCommunity() {
		return communityMapper.getNoticeCommunity().stream()
			.map(BoardDto::of)
			.collect(Collectors.toList());
	}

	@Override
	public List<BoardDto> searchBoard(String searchCondition, Object keyword) {
		Map<String, Object> map = new HashMap<>();
		map.put("searchCondition", searchCondition);
		if (Objects.equals(searchCondition, "memberSeq")) {
			map.put("keyword", Integer.parseInt((String) keyword));
		} else {
			map.put("keyword", keyword);
		}
		return communityMapper.searchBoard(map).stream()
			.map(BoardDto::of)
			.collect(Collectors.toList());
	}

	@Override
	public List<BoardDto> searchLikeBoard(String searchCondition, Object keyword, int likeCount) {
		return null;
	}

	@Override
	public List<BoardDto> searchNoticeBoard(String searchCondition, Object keyword) {
		return null;
	}

	@Override
	public Map<Long, Integer> getCntOfComment(List<Long> boardSeqList) {
		Map<Long, Integer> resultMap = new HashMap<>();
		for (Long aLong : boardSeqList) {
			int cnt = communityMapper.getCntOfComment(aLong);
			resultMap.put(aLong, cnt);
		}
		return resultMap;
	}

	@Override
	public BoardDto getCommunityByIdx(Long boardSeq) {
		return BoardDto.of(communityMapper.getCommunityByIdx(boardSeq));
	}

	@Override
	public List<BoardDto> getCommunity(Long memberSeq) {
		return communityMapper.getCommunity(memberSeq).stream()
			.map(BoardDto::of)
			.collect(Collectors.toList());
	}

	@Override
	public boolean updateCommunity(BoardDto boardDto) {
		return communityMapper.updateCommunity(BoardDao.of(boardDto));
	}

	@Override
	public boolean deleteCommunity(Long boardSeq) {
		return communityMapper.deleteCommunity(boardSeq);
	}

	@Override
	public boolean addBoardViewCnt(Long boardSeq) {
		return communityMapper.addBoardViewCnt(boardSeq);
	}

	@Override
	public List<BoardLikeDto> getBoardLikeCnt(Long boardSeq) {
		return communityMapper.getBoardLikeCnt(boardSeq).stream()
			.map(BoardLikeDto::of)
			.collect(Collectors.toList());
	}

	@Override
	public boolean setBoardLike(BoardLikeDto boardLikeDto) {
		return communityMapper.setBoardLike(BoardLikeDao.of(boardLikeDto));
	}

	@Override
	public boolean deleteBoardLike(BoardLikeDto boardLikeDto) {
		return communityMapper.deleteBoardLike(BoardLikeDao.of(boardLikeDto));
	}

	@Override
	public Long setComment(CommentDto commentDto) {
		boolean result = communityMapper.setComment(CommentDao.of(commentDto));

		List<PointHistoryDto> pointHistoryDtoList = memberMapper.getPointHistoryToday(commentDto.getMemberSeq()).stream().map(PointHistoryDto::of).collect(Collectors.toList());
		int communityCommentCnt = 0;

		for (int i = 0; i < pointHistoryDtoList.size(); i++) {
			if (pointHistoryDtoList.get(i).getPointHistory() == PointHistory.COMMUNITY_COMMENT) {
				communityCommentCnt++;
			}
		}

		PointHistoryDto resultHistoryDto;
		PointHistoryDto pointHistoryDto = new PointHistoryDto();
		// PointHistory Set
		pointHistoryDto.setMemberSeq(commentDto.getMemberSeq());
		pointHistoryDto.setPointHistory(PointHistory.COMMUNITY_COMMENT);
		pointHistoryDto.setHistoryComment(PointHistory.COMMUNITY_COMMENT.getText());
		pointHistoryDto.setBoardSeq(commentDto.getCommentSeq());
		if (communityCommentCnt >= 3) {
			pointHistoryDto.setPoint(0);
		} else {
			pointHistoryDto.setPoint(PointHistory.COMMUNITY_COMMENT.getPoint());
		}

		resultHistoryDto = PointHistory.getResultComment(pointHistoryDto);

		memberMapper.setPointHistory(PointHistoryDao.of(resultHistoryDto));

		// Level and Point Set
		memberService.updateLevelByPoint(commentDto.getMemberSeq(), pointHistoryDto.getPoint());

		// Notification Set
		NotificationDto notificationDto = new NotificationDto();
		notificationDto.setMemberSeq(commentDto.getMemberSeq());
		notificationDto.setType(NotificationType.COMMUNITY_COMMENT);
		notificationDto.setMessage(resultHistoryDto.getResultComment());
		notificationDto.setBoardSeq(resultHistoryDto.getBoardSeq());
		memberMapper.setNotification(NotificationDao.of(notificationDto));

		if (commentDto.getCommentSeq() != null) {
			return commentDto.getCommentSeq();
		} else {
			return 0L;
		}
	}

	@Override
	public List<CommentDto> getComment(Long boardSeq) {
		return communityMapper.getComment(boardSeq).stream()
			.map(CommentDto::of)
			.collect(Collectors.toList());
	}

	@Override
	public boolean updateComment(CommentDto commentDto) {
		return false;
	}

	@Override
	public boolean deleteComment(Long commentSeq) {
		return communityMapper.deleteComment(commentSeq);
	}

	@Override
	public Long setChildComment(ChildCommentDto childCommentDto) {
		communityMapper.setChildComment(ChildCommentDao.of(childCommentDto));

		if (childCommentDto.getChildCommentSeq() != null) {
			return childCommentDto.getChildCommentSeq();
		} else {
			return 0L;
		}
	}

	@Override
	public List<ChildCommentDto> getChildComment(Long boardSeq, Long commentSeq) {
		return communityMapper.getChildComment(boardSeq, commentSeq).stream()
			.map(ChildCommentDto::of)
			.collect(Collectors.toList());
	}

	@Override
	public boolean updateChildComment(ChildCommentDto childCommentDto) {
		return false;
	}

	@Override
	public boolean deleteChildComment(Long childCommentSeq) {
		return communityMapper.deleteChildComment(childCommentSeq);
	}
}
