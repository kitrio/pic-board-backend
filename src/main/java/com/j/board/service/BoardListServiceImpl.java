package com.j.board.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import com.j.board.domain.BoardVO;
import com.j.board.persistence.BoardMapper;

@Service
public class BoardListServiceImpl implements BoardListService{

	private final BoardMapper boardMapper;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public BoardListServiceImpl(BoardMapper boardMapper) {
		this.boardMapper = boardMapper;
	}

	@Override
	public boolean contentWriteService(BoardVO contentVO) {
		return boardMapper.insertContent(contentVO);
	}

	@Override
	public int contentModifyService(BoardVO contentVO) {
		return boardMapper.updateContent(contentVO);
	}

	@Override
	public BoardVO contentReadService(int boardNum) {
			contentCountUp(boardNum);
		return boardMapper.selectOneContent(boardNum);
	}

	@Override
	public boolean contentDelete(int boardNum, String memberId) {
		if(memberId.equals(contentReadService(boardNum).getMemberId())){
			return boardMapper.deleteContent(boardNum, memberId) == 1;
		}
		return false;
	}

	@Override
	public int contentCountUp(int boardNum) {
		return boardMapper.updateReadCount(boardNum);
	}

	@Override
	public int contentGoodCount(int boardNum) {
		return boardMapper.updateGoodCount(boardNum);
	}

	@Override
	public List<BoardVO> contentBestReadService(LocalDate searchDate) {
		List<BoardVO> contents;
//		try {
			contents = boardMapper.selectWeeklyBestList(getStartDay(searchDate), getLastDay(searchDate));
//		} catch (Exception e) {
//			return null;
//		}
		return contents;
	}

	@Override
	public List<BoardVO> contentListReadService(int firstPage, int lastPage) {
		List<BoardVO> contents;
		try {
			contents = boardMapper.selectContentsList(firstPage, lastPage);
		} catch (Exception e) {
			return null;
		}                                                                             
		return contents;
	}

	@Override
	public List<BoardVO> contentSearchByTitle(String title, int startPage, int endPage) {
		List<BoardVO> contents;
		try {
			contents = boardMapper.selectTitleSearch(title, startPage, endPage);
		} catch (Exception e) {
				return null;
		}
		return contents;
	}


	private Timestamp getStartDay(LocalDate searchDate) {
		LocalDate firstDay = searchDate.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
		return Timestamp.valueOf(firstDay.atStartOfDay());

	}

	private Timestamp getLastDay(LocalDate searchDate){
		LocalDate lastDay = searchDate.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
		return Timestamp.valueOf(lastDay.atStartOfDay());

	}
}
