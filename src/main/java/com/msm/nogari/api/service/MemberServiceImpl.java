package com.msm.nogari.api.service;

import com.msm.nogari.core.dao.member.MemberDao;
import com.msm.nogari.core.dto.member.*;
import com.msm.nogari.core.enums.MemberStatus;
import com.msm.nogari.core.enums.NotificationType;
import com.msm.nogari.core.enums.PointHistory;
import com.msm.nogari.core.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * @author 최대희
 * @since 2023-11-28
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	private final MemberMapper memberMapper;

	private final PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public Object memberRegistration(MemberDto memberDto, HttpServletRequest httpServletRequest) {
		String encodedPassword = passwordEncoder.encode(memberDto.getEmail());
		memberDto.setIpAddr(httpServletRequest.getRemoteAddr());

		MemberDao memberDao = memberMapper.getMemberInfoByUsername(memberDto.getEmail());

		if (memberDao == null) {
			// MemberDao 만들어주기
			MemberDao saveData = MemberDao.of(memberDto, encodedPassword);

			if (memberMapper.memberRegistration(saveData)) {
				// PointHistory Set
				PointHistoryDto pointHistoryDto = new PointHistoryDto();
				pointHistoryDto.setMemberSeq(saveData.getMemberSeq());
				pointHistoryDto.setPoint(PointHistory.ATTENDANCE.getPoint());
				pointHistoryDto.setPointHistory(PointHistory.ATTENDANCE);
				pointHistoryDto.setHistoryComment(PointHistory.ATTENDANCE.getText());
				pointHistoryDto.setBoardSeq(0L);

				PointHistoryDto resultHistoryDto = PointHistory.getResultComment(pointHistoryDto);

				memberMapper.setPointHistory(resultHistoryDto);

				// Level and Point Set
				// 여기는 level table 에 들어갈 데이터 set 첫 가입이기에 무조건 1레벨, 출석 10포인트
				LevelDto levelDto = new LevelDto();
				levelDto.setMemberSeq(saveData.getMemberSeq());
				levelDto.setPoint(resultHistoryDto.getPoint());
				levelDto.setLevel(1);
				memberMapper.setLevelAndPoint(levelDto);

				// Notification Set
				NotificationDto notificationDto = new NotificationDto();
				notificationDto.setMemberSeq(saveData.getMemberSeq());
				notificationDto.setType(NotificationType.REGISTRATION);
				notificationDto.setMessage("노가리 가입을 축하드립니다!");
				notificationDto.setBoardSeq(resultHistoryDto.getBoardSeq());
				memberMapper.setNotification(notificationDto);

				// 가입때는 이례적으로 2개의 notification 이 set 됨 가입, 출석
				notificationDto.setType(NotificationType.ATTENDANCE);
				notificationDto.setMessage(resultHistoryDto.getResultComment());
				memberMapper.setNotification(notificationDto);

				// 회원가입이 됐으면 memberSeq 를 리턴해서 Flutter sharedpreference 에 저장해줘야함
				return saveData.getMemberSeq();
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public MemberStatus getMemberStatus(MemberDto memberDto) {
		// email, device, identifier 데이터 들어와야함
		// ios 회원
		if (Objects.equals(memberDto.getDevice(), "iOS")) {
			if (memberDto.getEmail() == null && memberDto.getIdentifier() == null) {
				// 이건 오류임 둘 다 null 이면 안됨
				return MemberStatus.UNIDENTIFIED;
			} else {
				// email 허용인 경우
				// 허용이든 아니든 mapper 에서 or 써서 확인가능
				// ios용 멤버확인 mapper 하나 만들면됨
				// ios용 멤버확인 mapper 필요 device == ios 의 경우 email or identifier 로 확인하면 될듯
				MemberDao memberDao = memberMapper.getIosMemberStatus(memberDto);
				if (memberDao == null) {
					return MemberStatus.UNSUBSCRIBED;
				} else {
					return memberDao.getStatus();
				}
			}
		} else {
			// android 회원
			MemberDao memberDao = memberMapper.isDuplicateEmail(memberDto.getEmail());
			if (memberDao == null) {
				return MemberStatus.UNSUBSCRIBED;
			} else {
				return memberDao.getStatus();
			}
		}
	}

	@Override
	public Long getMemberSeqByEmail(String email) {
		MemberDao memberDao = memberMapper.getMemberInfoByUsername(email);
		return memberDao.getMemberSeq();
	}

	@Override
	public boolean isDuplicate(String type, String value) {
		MemberDao memberDao;
		if (Objects.equals(type, "email")) {
			memberDao = memberMapper.isDuplicateEmail(value);
		} else {
			memberDao = memberMapper.isDuplicateNickName(value);
		}
		// memberDao 가 null 이 아니면 중복이므로 true , 아니면 false
		return memberDao != null;
	}

	@Override
	public String getDeviceToken(Long memberSeq) {
		return memberMapper.getDeviceToken(memberSeq);
	}

	@Override
	public boolean changeDeviceToken(MemberDto memberDto) {
		return memberMapper.updateDeviceToken(memberDto);
	}

	@Override
	public MemberDao getMemberInfo(Long memberSeq) {
		return memberMapper.getMemberInfo(memberSeq);
	}

	@Override
	public boolean updateNickName(MemberDto memberDto) {
		return memberMapper.updateNickName(memberDto);
	}

	@Override
	public boolean restoreMember(MemberDto memberDto) {
		return false;
	}

	@Override
	public boolean withdrawalMember(WithdrawalMemberDto withdrawalMemberDto) throws Exception {
		try {
			boolean deleteResult = memberMapper.withdrawalMember(withdrawalMemberDto);
			boolean updateResult = memberMapper.updateStatus(withdrawalMemberDto.getMemberSeq(), MemberStatus.WITHDRAWAL);
			return deleteResult && updateResult;
		} catch (Exception e) {
			log.error(String.valueOf(e));
			throw new Exception(e);
		}
	}

	@Override
	public Long blockMember(BlockDto blockDto) {
		return memberMapper.blockMember(blockDto);
	}

	@Override
	public List<BlockDto> getBlockMember(Long memberSeq) {
		return memberMapper.getBlockMember(memberSeq);
	}

	@Override
	public boolean deleteBlockMember(List<BlockDto> blockDtoList) {
		return memberMapper.deleteBlockMember(blockDtoList);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean setPoint(PointHistoryDto pointHistoryDto) {
		// 여기서 먼저 광고로 왔느냐 출석으로 왔느냐 확인, 글쓰기, 댓글의 경우 작성할 때 이미 처리를 하기 때문에 여기서는 오지 않음

		List<PointHistoryDto> pointHistoryDtoList = memberMapper.getPointHistoryToday(pointHistoryDto.getMemberSeq());

		if (pointHistoryDto.getPointHistory() == PointHistory.ATTENDANCE) {
			int attendanceCnt = 0;

			for (int i = 0; i < pointHistoryDtoList.size(); i++) {
				if (pointHistoryDtoList.get(i).getPointHistory() == PointHistory.ATTENDANCE) {
					attendanceCnt++;
					break;
				}
			}

			if (attendanceCnt == 1) {
				// 이미 오늘 출석을 진행했으므로 빠져나감, 이미 API를 호출할때 프론트에서 체크하고 오겠지만 크로스 체킹 개념
				return true;
			} else {
				PointHistoryDto resultHistoryDto;
				PointHistoryDto updatePointHistoryDto = new PointHistoryDto();
				// PointHistory Set
				updatePointHistoryDto.setMemberSeq(pointHistoryDto.getMemberSeq());
				updatePointHistoryDto.setPointHistory(PointHistory.ATTENDANCE);
				updatePointHistoryDto.setHistoryComment(PointHistory.ATTENDANCE.getText());
				updatePointHistoryDto.setBoardSeq(pointHistoryDto.getBoardSeq());

				updatePointHistoryDto.setPoint(PointHistory.ATTENDANCE.getPoint());

				resultHistoryDto = PointHistory.getResultComment(updatePointHistoryDto);

				memberMapper.setPointHistory(resultHistoryDto);

				// Level and Point Set
				updateLevelByPoint(pointHistoryDto.getMemberSeq(), updatePointHistoryDto.getPoint());

				// Notification Set
				NotificationDto notificationDto = new NotificationDto();
				notificationDto.setMemberSeq(pointHistoryDto.getMemberSeq());
				notificationDto.setType(NotificationType.ATTENDANCE);
				notificationDto.setMessage(resultHistoryDto.getResultComment());
				notificationDto.setBoardSeq(resultHistoryDto.getBoardSeq());
				memberMapper.setNotification(notificationDto);

				return true;
			}
		} else {
			PointHistoryDto resultHistoryDto;
			PointHistoryDto updatePointHistoryDto = new PointHistoryDto();
			// PointHistory Set
			updatePointHistoryDto.setMemberSeq(pointHistoryDto.getMemberSeq());
			updatePointHistoryDto.setBoardSeq(pointHistoryDto.getBoardSeq());

			switch (pointHistoryDto.getPointHistory()) {
				case WATCH_5SEC_AD:
					int watch5SecCnt = 0;

					for (int i = 0; i < pointHistoryDtoList.size(); i++) {
						if (pointHistoryDtoList.get(i).getPointHistory() == PointHistory.WATCH_5SEC_AD) {
							watch5SecCnt++;
						}
					}

					if (watch5SecCnt >= 5) {
						updatePointHistoryDto.setPoint(0);
					} else {
						updatePointHistoryDto.setPoint(PointHistory.WATCH_5SEC_AD.getPoint());
						updatePointHistoryDto.setPointHistory(PointHistory.WATCH_5SEC_AD);
						updatePointHistoryDto.setHistoryComment(PointHistory.WATCH_5SEC_AD.getText());
						resultHistoryDto = PointHistory.getResultComment(updatePointHistoryDto);

						memberMapper.setPointHistory(resultHistoryDto);

						// Level and Point Set
						updateLevelByPoint(pointHistoryDto.getMemberSeq(), updatePointHistoryDto.getPoint());

						// Notification Set
						NotificationDto notificationDto = new NotificationDto();
						notificationDto.setMemberSeq(pointHistoryDto.getMemberSeq());
						notificationDto.setType(NotificationType.WATCH_5SEC_AD);
						notificationDto.setMessage(resultHistoryDto.getResultComment());
						notificationDto.setBoardSeq(resultHistoryDto.getBoardSeq());
						memberMapper.setNotification(notificationDto);
					}
					break;
				case WATCH_30SEC_AD:
					int watch30SecCnt = 0;

					for (int i = 0; i < pointHistoryDtoList.size(); i++) {
						if (pointHistoryDtoList.get(i).getPointHistory() == PointHistory.WATCH_30SEC_AD) {
							watch30SecCnt++;
						}
					}

					if (watch30SecCnt >= 3) {
						updatePointHistoryDto.setPoint(0);
					} else {
						updatePointHistoryDto.setPoint(PointHistory.WATCH_30SEC_AD.getPoint());
						updatePointHistoryDto.setPointHistory(PointHistory.WATCH_30SEC_AD);
						updatePointHistoryDto.setHistoryComment(PointHistory.WATCH_30SEC_AD.getText());
						resultHistoryDto = PointHistory.getResultComment(updatePointHistoryDto);

						memberMapper.setPointHistory(resultHistoryDto);

						// Level and Point Set
						updateLevelByPoint(pointHistoryDto.getMemberSeq(), updatePointHistoryDto.getPoint());

						// Notification Set
						NotificationDto notificationDto = new NotificationDto();
						notificationDto.setMemberSeq(pointHistoryDto.getMemberSeq());
						notificationDto.setType(NotificationType.WATCH_30SEC_AD);
						notificationDto.setMessage(resultHistoryDto.getResultComment());
						notificationDto.setBoardSeq(resultHistoryDto.getBoardSeq());
						memberMapper.setNotification(notificationDto);
					}
					break;
				case COMMUNITY_WRITING:
					int communityWritingCnt = 0;

					for (int i = 0; i < pointHistoryDtoList.size(); i++) {
						if (pointHistoryDtoList.get(i).getPointHistory() == PointHistory.COMMUNITY_WRITING) {
							communityWritingCnt++;
						}
					}

					if (communityWritingCnt >= 3) {
						updatePointHistoryDto.setPoint(0);
					} else {
						updatePointHistoryDto.setPoint(PointHistory.COMMUNITY_WRITING.getPoint());
						updatePointHistoryDto.setPointHistory(PointHistory.COMMUNITY_WRITING);
						updatePointHistoryDto.setHistoryComment(PointHistory.COMMUNITY_WRITING.getText());
						resultHistoryDto = PointHistory.getResultComment(updatePointHistoryDto);

						memberMapper.setPointHistory(resultHistoryDto);

						// Level and Point Set
						updateLevelByPoint(pointHistoryDto.getMemberSeq(), updatePointHistoryDto.getPoint());

						// Notification Set
						NotificationDto notificationDto = new NotificationDto();
						notificationDto.setMemberSeq(pointHistoryDto.getMemberSeq());
						notificationDto.setType(NotificationType.COMMUNITY_BOARD);
						notificationDto.setMessage(resultHistoryDto.getResultComment());
						notificationDto.setBoardSeq(resultHistoryDto.getBoardSeq());
						memberMapper.setNotification(notificationDto);
					}
					break;
				case COMMUNITY_COMMENT:
					int communityCommentCnt = 0;

					for (int i = 0; i < pointHistoryDtoList.size(); i++) {
						if (pointHistoryDtoList.get(i).getPointHistory() == PointHistory.COMMUNITY_COMMENT) {
							communityCommentCnt++;
						}
					}

					if (communityCommentCnt >= 5) {
						updatePointHistoryDto.setPoint(0);
					} else {
						updatePointHistoryDto.setPoint(PointHistory.COMMUNITY_COMMENT.getPoint());
						updatePointHistoryDto.setPointHistory(PointHistory.COMMUNITY_COMMENT);
						updatePointHistoryDto.setHistoryComment(PointHistory.COMMUNITY_COMMENT.getText());
						resultHistoryDto = PointHistory.getResultComment(updatePointHistoryDto);

						memberMapper.setPointHistory(resultHistoryDto);

						// Level and Point Set
						updateLevelByPoint(pointHistoryDto.getMemberSeq(), updatePointHistoryDto.getPoint());

						// Notification Set
						NotificationDto notificationDto = new NotificationDto();
						notificationDto.setMemberSeq(pointHistoryDto.getMemberSeq());
						notificationDto.setType(NotificationType.COMMUNITY_COMMENT);
						notificationDto.setMessage(resultHistoryDto.getResultComment());
						notificationDto.setBoardSeq(resultHistoryDto.getBoardSeq());
						memberMapper.setNotification(notificationDto);
					}
					break;
				case REVIEW_WRITING:
					int reviewWritingCnt = 0;

					for (int i = 0; i < pointHistoryDtoList.size(); i++) {
						if (pointHistoryDtoList.get(i).getPointHistory() == PointHistory.REVIEW_WRITING) {
							reviewWritingCnt++;
						}
					}

					if (reviewWritingCnt >= 3) {
						updatePointHistoryDto.setPoint(0);
					} else {
						updatePointHistoryDto.setPoint(PointHistory.REVIEW_WRITING.getPoint());
						updatePointHistoryDto.setPointHistory(PointHistory.REVIEW_WRITING);
						updatePointHistoryDto.setHistoryComment(PointHistory.REVIEW_WRITING.getText());
						resultHistoryDto = PointHistory.getResultComment(updatePointHistoryDto);

						memberMapper.setPointHistory(resultHistoryDto);

						// Level and Point Set
						updateLevelByPoint(pointHistoryDto.getMemberSeq(), updatePointHistoryDto.getPoint());

						// Notification Set
						NotificationDto notificationDto = new NotificationDto();
						notificationDto.setMemberSeq(pointHistoryDto.getMemberSeq());
						notificationDto.setType(NotificationType.REVIEW_BOARD);
						notificationDto.setMessage(resultHistoryDto.getResultComment());
						notificationDto.setBoardSeq(resultHistoryDto.getBoardSeq());
						memberMapper.setNotification(notificationDto);
					}
					break;
				case REVIEW_COMMENT:
					int reviewCommentCnt = 0;

					for (int i = 0; i < pointHistoryDtoList.size(); i++) {
						if (pointHistoryDtoList.get(i).getPointHistory() == PointHistory.REVIEW_COMMENT) {
							reviewCommentCnt++;
						}
					}

					if (reviewCommentCnt >= 5) {
						updatePointHistoryDto.setPoint(0);
					} else {
						updatePointHistoryDto.setPoint(PointHistory.REVIEW_COMMENT.getPoint());
						updatePointHistoryDto.setPointHistory(PointHistory.REVIEW_COMMENT);
						updatePointHistoryDto.setHistoryComment(PointHistory.REVIEW_COMMENT.getText());
						resultHistoryDto = PointHistory.getResultComment(updatePointHistoryDto);

						memberMapper.setPointHistory(resultHistoryDto);

						// Level and Point Set
						updateLevelByPoint(pointHistoryDto.getMemberSeq(), updatePointHistoryDto.getPoint());

						// Notification Set
						NotificationDto notificationDto = new NotificationDto();
						notificationDto.setMemberSeq(pointHistoryDto.getMemberSeq());
						notificationDto.setType(NotificationType.REVIEW_COMMENT);
						notificationDto.setMessage(resultHistoryDto.getResultComment());
						notificationDto.setBoardSeq(resultHistoryDto.getBoardSeq());
						memberMapper.setNotification(notificationDto);
					}
					break;
			}
			return true;
		}
	}

	@Override
	public LevelDto getLevelAndPoint(Long memberSeq) {
		return memberMapper.getLevelAndPoint(memberSeq);
	}

	@Override
	public List<PointHistoryDto> getPointHistory(Long memberSeq) {
		return memberMapper.getPointHistory(memberSeq);
	}

	@Override
	public List<PointHistoryDto> getPointHistoryToday(Long memberSeq) {
		return memberMapper.getPointHistoryToday(memberSeq);
	}

	@Override
	public boolean setPointHistory(PointHistoryDto pointHistoryDto) {
		return memberMapper.setPointHistory(pointHistoryDto);
	}

	@Override
	public List<NotificationDto> getNotification(Long memberSeq) {
		return memberMapper.getNotification(memberSeq);
	}

	@Override
	public boolean updateLevelByPoint(Long memberSeq, int point) {
		// 기존 level, point 데이터 가져오기
		LevelDto levelDto = memberMapper.getLevelAndPoint(memberSeq);

		if (levelDto == null) {
			return false;
		}

		// 현재 레벨과 포인트
		int currentLevel = levelDto.getLevel();
		int currentPoint = levelDto.getPoint();

		// 누적된 포인트와 레벨 업그레이드에 필요한 구간 포인트
		int accumulatedPoint = currentPoint + point;
		int pointsNeededForUpgrade = 100;

		// 현재 포인트에 새로운 포인트를 추가 (매퍼에 저장할 포인트)
		currentPoint += point;

		// 누적된 포인트로 레벨 업그레이드 계산 및 처리
		while (accumulatedPoint >= pointsNeededForUpgrade) {
			currentLevel++;
			accumulatedPoint -= pointsNeededForUpgrade;
		}

		System.out.println("테스트");
		System.out.println("currentLevel = " + currentLevel);
		System.out.println("currentPoint = " + currentPoint);
		System.out.println("accumulatedPoint = " + accumulatedPoint);

		LevelDto resultDto = new LevelDto();
		resultDto.setLevelSeq(levelDto.getLevelSeq());
		resultDto.setMemberSeq(levelDto.getMemberSeq());
		resultDto.setLevel(currentLevel);
		resultDto.setPoint(currentPoint);

		// 레벨과 포인트 업데이트
		return memberMapper.updateLevelAndPoint(resultDto);
	}

	@Override
	public MemberDao getMemberInfoByUsername(String username) {
		return memberMapper.getMemberInfoByUsername(username);
	}
}
