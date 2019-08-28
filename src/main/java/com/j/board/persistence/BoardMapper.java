package com.j.board.persistence;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.sql.Timestamp;
import java.util.List;

import com.j.board.domain.BoardVO;
import com.j.board.domain.FilesVO;

@Mapper
public interface BoardMapper {
    @Insert("insert into board(boardNum, nickname, memberid, title, content, readCount, goodCount, ip, filealtname) VALUES(#{boardNum}, #{nickname}, #{memberId}, #{title}, #{content}, #{readCount}, #{goodCount}, #{ip}, #{fileAltName})")
    public boolean insertContent(BoardVO board);

    @Insert("insert into files (filename, filealtname, filepath) VALUES(#{fileName}, #{fileAltName}, #{filePath} )")
    public int insertFile(FilesVO files);

    @Select("select boardnum, nickname, memberid, title, content, (DATE_FORMAT(writetime, '%Y/%m/%d %h:%i')) as writeTime, readCount, goodCount, filealtname from board where boardnum = #{boardNum}")
    public BoardVO selectOneContent(int boardNum);

    @Select("select * from board where boardnum = #{boardNum} limit #{firstPage}, #{lastPage}")
    public List<BoardVO> selectContentsList (@Param("firstPage") int firstPage, @Param("lastPage") int lastPage);
    
    @Select("select * from board where writetime between #{startDay} AND #{endDay} order by goodcount desc LIMIT 0, 19")
    public List<BoardVO> selectWeeklyBestList (@Param("startDay") Timestamp statDay, @Param("endDay") Timestamp endDay);

    @Update("update board content = #{content}, title= #{title}, filealtname= #{fileAltName}, where boardnum = #{boardNum} ")
    public boolean updateContent(BoardVO board);

    @Update("update files set boardnum = #{boardNum}, where filealtname = #{fileAltName}")
    public void setRealfileNum(int boardNum, String fileAltName);
    
    @Update("update board set goodCount =+1 where boardnum = #{boardNum} ")
    public int updateGoodCount(int boardNum);

    @Update("update board set readcount =+1 where boardnum = #{boardNum}")
    public int updateReadCount(int boardNum);

    @Delete("delete from board where boardnum = #{boardNum} ")
    public int deleteContent(int boardnum);

}