package com.j.board.persistence;

import com.j.board.domain.MemberVO;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MemberMapper {
  @Select("select * from member where memberid = #{memberId}")
  public MemberVO findByUserid(@Param("memberId") String memberId);

  @Insert("insert into member (memberid, password, nickname) values(#{memberId}, #{password}, #{nickname})")
  public boolean signUpMember(MemberVO vo);
}