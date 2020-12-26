package com.j.board.persistence;

import java.util.List;

import com.j.board.domain.BoardVO;
import com.j.board.domain.MemberVO;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MemberMapper {
  @Select("select * from member where memberid = #{memberId}")
  MemberVO findByUserid(@Param("memberId") String memberId);

  @Select("select boardnum, title, content, filealtname from board inner join member on member.memberid = board.memberid where member.nickname = #{nickname} ")
  List<BoardVO> selectByMemberContent(@Param("nickname") String nickname);

  @Select("select memberid, nickname, regdate, lastlogin from member where memberid = #{memberId}")
  MemberVO selectMyInfo(@Param("memberId") String memberId);

  @Insert("insert into member (memberid, password, nickname, authinfo) values(#{memberId}, #{password}, #{nickname}, #{authInfo})")
  boolean signUpMember(MemberVO vo);

  @Delete("delete from member where memberid = #{memberId}")
  int deleteMember(@Param("memberId") String memberId);
}