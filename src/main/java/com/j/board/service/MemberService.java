package com.j.board.service;

import java.util.List;

import com.j.board.domain.MemberVO;

public interface MemberService {
  public boolean signUpMember(MemberVO member);
	public List<Object> searchMemberContents(String nickname);
}