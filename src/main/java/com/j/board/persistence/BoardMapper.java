package com.j.board.persistence;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import com.j.board.domain.BoardVO;
import com.j.board.domain.FilesVO;


@Mapper
public interface BoardMapper {
	@Insert("insert into board(boardNum, writer, title, content, readCount, goodCount, ip) VALUES(#{boardNum}, #{nickname}, #{title}, #{content}, #{readCount}, #{goodCount}, #{ip})")
    public boolean insertContent(BoardVO board);

    @Insert("insert into files (boardnum, filename, filealtname, filepath) VALUSE(#{boardNum}, #{filename}, #{fileAltName}, #{filePath}")
    public boolean insertFile(FilesVO files);
    
    @Update("update files boardnum = #{boardNum}, where filealtname = #{fileAltName}")
	public void setRealfileNum(int boardNum, String fileAltName);

}
