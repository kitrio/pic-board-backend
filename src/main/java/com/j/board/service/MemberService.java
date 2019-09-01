package com.j.board.service;

import java.util.List;

import com.j.board.domain.BoardVO;
import com.j.board.domain.MemberVO;

public interface MemberService {
  public boolean signUpMember(MemberVO member);
  public List<BoardVO>searchMemberContents(String nickname);
  public boolean deleteMember(String memberid);
}