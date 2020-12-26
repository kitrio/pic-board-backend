package com.j.board.service;

import java.time.LocalDate;
import java.util.List;

import com.j.board.domain.BoardVO;

public interface BoardListService {
	boolean contentWriteService(BoardVO contentVO);
	int contentModifyService(BoardVO contentVO);
	BoardVO contentReadService(int boardNum);
	boolean contentGoodCount(int boardNum, String memberId);
	void contentCountUp(int boardNum);
	List<BoardVO> contentListReadService(int firstPage, int lastPage);
	List<BoardVO> contentBestReadService(LocalDate searchDate);
	List<BoardVO> contentSearchByTitle(String title, int startPage, int endPage);
	boolean contentDelete(int boardNum, String memberId);
}
