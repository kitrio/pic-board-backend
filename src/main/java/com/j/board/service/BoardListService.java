package com.j.board.service;

import com.j.board.domain.BoardVO;

public interface BoardListService {
	public boolean contentWriteService(BoardVO contentVO);
	public boolean contentModifyService(BoardVO contentVO);
}
