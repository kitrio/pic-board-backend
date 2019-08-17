package com.j.board.persistence;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.j.board.domain.BoardVO;


@Mapper
public interface BoardMapper {
	@Insert("insert into board(boardNum, title, content, readCount, goodCount, ip) VALUES(#{boardNum}, #{writer}, #{title}, #{content}, #{readCount}, #{goodCount}, #{ip})")
    public void insertContent(BoardVO vo);

}
