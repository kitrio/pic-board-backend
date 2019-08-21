package com.j.board.service;

import java.util.Date;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.j.board.domain.BoardVO;
import com.j.board.persistence.BoardMapper;

@Service
public class BoardListServiceImpl implements BoardListService{
	@Autowired
	BoardMapper boardMapper;
	public void writeService(BoardVO contentVO) {
		Date systemTime = new Date();
        Timestamp date = new java.sql.Timestamp(systemTime.getTime());
		contentVO.setWriteTime(date);
		boardMapper.insertContent(contentVO);
	}
}
