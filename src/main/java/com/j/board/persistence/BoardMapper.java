package com.j.board.persistence;

import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

import com.j.board.domain.BoardVO;
import com.j.board.domain.FilesVO;

@Mapper
public interface BoardMapper {
    @Insert("insert into board(boardnum, nickname, memberid, title, content, readCount, goodCount, ip, filealtname) VALUES(#{boardNum}, #{nickname}, #{memberId}, #{title}, #{content}, #{readCount}, #{goodCount}, #{ip}, #{fileAltName})")
    boolean insertContent(BoardVO board);

    @Insert("insert into files (filename, filealtname, filepath) VALUES(#{fileName}, #{fileAltName}, #{filePath} )")
    int insertFile(FilesVO files);

    @Insert("insert into goodcount(memberid, boardNum) VALUES(#{memberId}, #{boardNum})")
    boolean insertGoodCount(String memberId, int boardNum);

    @Select("select boardnum, nickname, memberid, title, content, (DATE_FORMAT(writetime, '%Y/%m/%d %h:%i')) as writeTime, readCount, goodCount, filealtname from board where boardnum = #{boardNum}")
    BoardVO selectOneContent(int boardNum);

    @Select("select boardnum, title, goodCount, filealtname from board order by boardnum desc limit #{firstPage}, #{lastPage}")
    List<BoardVO> selectContentsList (@Param("firstPage") int firstPage, @Param("lastPage") int lastPage);
    
    @Select("select boardnum, title, goodCount, filealtname from board where writetime between #{startDay} and #{endDay} order by goodcount desc limit 0, 19")
    List<BoardVO> selectWeeklyBestList(@Param("startDay") Timestamp statDay, @Param("endDay") Timestamp endDay);

    @Select("select title, content, readCount, goodCount, filealtname from board where title LIKE CONCAT('%', #{keyword},'%') limit #{startPage}, #{endPage}")
    List<BoardVO> selectTitleSearch(@Param("keyword") String title, @Param("startPage") int startPage, @Param("endPage") int endPage);

    @Select("select memberid, boardnum from goodcount where memberid = #{memberId} and boardnum = #{boardNum}")
    Integer checkGoodCount(@Param("memberId") String memberId, @Param("boardNum") int boardNum);

    @Update("update board set content = #{content}, title= #{title}, filealtname= #{fileAltName} where boardnum = #{boardNum} ")
    int updateContent(BoardVO board);
    
    @Update("update board set goodCount = goodCount +1 where boardnum = #{boardNum} ")
    int updateGoodCount(int boardNum);

    @Update("update board set readcount = readcount +1 where boardnum = #{boardNum}")
    int updateReadCount(int boardNum);

    @Delete("delete from board where boardnum = #{boardNum} and memberid = #{memberid} ")
    int deleteContent(int boardNum, String memberid);

}