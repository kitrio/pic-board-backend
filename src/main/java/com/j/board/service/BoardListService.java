package com.j.board.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import com.j.board.domain.BoardVO;

public interface BoardListService {
	public boolean contentWriteService(BoardVO contentVO);
	public int contentModifyService(BoardVO contentVO);
	public BoardVO contentReadService(int boardNum);
	public int contentGoodCount(int boardNum);
	public int contentCountUp(int boardNum);
	public List<BoardVO> contentListReadService(int firstPage, int lastPage);
	public List<BoardVO> contentBestReadService(LocalDate searchDate);
	public List<BoardVO> contentSearchByTitle(String title, int startPage, int endPage);
	public boolean contentDelete(int boardNum);
	public Timestamp getStartDay(LocalDate searchDate);
	public Timestamp getLastDay(LocalDate searchDate);

}
