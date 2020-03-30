package com.j.board.service;

import java.util.List;

import com.j.board.domain.BoardVO;
import com.j.board.domain.MemberVO;

public interface MemberService {
  boolean signUpMember(MemberVO member);
  List<BoardVO>searchMemberContents(String nickname);
  boolean deleteMember(String memberid);
}