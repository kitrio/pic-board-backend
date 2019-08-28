package com.j.board.service;

import org.springframework.beans.factory.annotation.Autowired;
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
	@Autowired
	BoardMapper boardMapper;
	
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

	public boolean contentDelete(int boardNum) {
		if(boardMapper.deleteContent(boardNum) == 1) {
			return true;
		} else {
			return false;
		}
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
		List<BoardVO> contents = null;
		try {
			contents = boardMapper.selectContentsList(firstPage, lastPage);
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
