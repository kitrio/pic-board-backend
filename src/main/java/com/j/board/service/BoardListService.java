package com.j.board.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import com.j.board.domain.BoardVO;

public interface BoardListService {
	public boolean contentWriteService(BoardVO contentVO);
	public boolean contentModifyService(BoardVO contentVO);
	public BoardVO contentReadService(int boardNum);
	public List<BoardVO> contentListReadService(int firstPage, int lastPage);
	public List<BoardVO> contentBestReadService(LocalDate searchDate);
	public Timestamp getStartDay(LocalDate searchDate);
	public Timestamp getLastDay(LocalDate searchDate);

}
