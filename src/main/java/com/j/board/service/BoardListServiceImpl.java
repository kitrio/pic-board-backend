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

	final BoardMapper boardMapper;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public BoardListServiceImpl(BoardMapper boardMapper) {
		this.boardMapper = boardMapper;
	}

	public boolean contentWriteService(BoardVO contentVO) {
		return boardMapper.insertContent(contentVO);
	}

	public int contentModifyService(BoardVO contentVO) {
		return boardMapper.updateContent(contentVO);
	}
	
	public BoardVO contentReadService(int boardNum) {
			contentCountUp(boardNum);
		return boardMapper.selectOneContent(boardNum);
	}

	public boolean contentDelete(int boardNum, String memberId) {
		if(memberId.equals(contentReadService(boardNum).getMemberId())){
			return boardMapper.deleteContent(boardNum, memberId) == 1;
		}
		return false;
	}

	public int contentCountUp(int boardNum) {
		return boardMapper.updateReadCount(boardNum);
	}

	public int contentGoodCount(int boardNum) {
		return boardMapper.updateGoodCount(boardNum);
	}

	public List<BoardVO> contentBestReadService(LocalDate searchDate) {
		List<BoardVO> contents = null;
		try {
			contents = boardMapper.selectWeeklyBestList(getStartDay(searchDate), getLastDay(searchDate));
		} catch (Exception e) {
			return null;
		}
		return contents;
	}

	public List<BoardVO> contentListReadService(int firstPage, int lastPage) {
		List<BoardVO> contents;
		try {
			contents = boardMapper.selectContentsList(firstPage, lastPage);
		} catch (Exception e) {
			return null;
		}                                                                             
		return contents;
	}

	public List<BoardVO> contentSearchByTitle(String title, int startPage, int endPage) {
		List<BoardVO> contents = null;
		try {
			contents = boardMapper.selectTitleSearch(title, startPage, endPage);
		} catch (Exception e) {
				return null;
		}
		return contents;
	}
	
	public Timestamp getStartDay(LocalDate searchDate) {
		LocalDate firstDay = searchDate.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
		Timestamp timestamp = Timestamp.valueOf(firstDay.atStartOfDay());
		return timestamp;
		
	}
	public Timestamp getLastDay(LocalDate searchDate){
		LocalDate lastDay = searchDate.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
		Timestamp timestamp = Timestamp.valueOf(lastDay.atStartOfDay());
		return timestamp;
	}
}
