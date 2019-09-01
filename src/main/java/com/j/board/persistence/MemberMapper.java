package com.j.board.persistence;

import java.util.List;

import com.j.board.domain.MemberVO;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MemberMapper {
  @Select("select * from member where memberid = #{memberId}")
  public MemberVO findByUserid(@Param("memberId") String memberId);

  @Select("select title, content, filealtname from board inner join member on member.memberid = board.memberid where member.nickname = #{nickname} ")
  public List<Object> selectByMemberContent(@Param("nickname") String nickname);

  @Insert("insert into member (memberid, password, nickname) values(#{memberId}, #{password}, #{nickname})")
  public boolean signUpMember(MemberVO vo);
}