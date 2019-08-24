package com.j.board.persistence;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.j.board.domain.BoardVO;
import com.j.board.domain.FilesVO;

@Mapper
public interface BoardMapper {
    @Insert("insert into board(boardNum, writer, memberid, title, content, readCount, goodCount, ip, filealtname) VALUES(#{boardNum}, #{nickname}, #{memberid} #{title}, #{content}, #{readCount}, #{goodCount}, #{ip}, #{filealtname})")
    public boolean insertContent(BoardVO board);

    @Insert("insert into files (filename, filealtname, filepath) VALUES(#{fileName}, #{fileAltName}, #{filePath} )")
    public int insertFile(FilesVO files);

    @Select("select * from board where boardnum = #{boardNum}")
    public BoardVO selectOneContent(int boardNum);

    @Update("update board content = #{content}, title= #{title}, filealtname= #{fileAltName}, where boardnum = #{boardNum} ")
    public boolean updateContent(BoardVO board);
    
    @Update("update files boardnum = #{boardNum}, where filealtname = #{fileAltName}")
    public void setRealfileNum(int boardNum, String fileAltName);
    

}
