package com.j.board.service;

import java.util.List;

import com.j.board.domain.BoardVO;
import com.j.board.domain.MemberVO;
import com.j.board.security.CustomMember;

public interface MemberService {
  List<BoardVO>searchMemberContents(String nickname);
  boolean signUpMember(MemberVO member);
  boolean deleteMember(String memberid);
  boolean modifyMember(MemberVO member);
  MemberVO getMyInfo(CustomMember principal, String password);
}