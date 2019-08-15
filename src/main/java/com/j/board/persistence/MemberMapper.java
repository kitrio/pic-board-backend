package com.j.board.persistence;

import com.j.board.domain.MemberVO;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MemberMapper {
  @Select("select * from member where userid = #{userid}")
  public MemberVO findByUserid(@Param("userid") String userid);

  @Insert("insert into member (memberid, passwd, nickname) values(#{memberId}, #{password}, #{nickname})")
  public boolean signUpMember(MemberVO vo);
}