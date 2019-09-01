package com.j.board.service;

import java.util.List;

import com.j.board.domain.MemberVO;
import com.j.board.persistence.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {
  @Autowired
  MemberMapper memberMapper;
  @Autowired
  PasswordEncoder bCryptPasswordEncoder;


  public boolean signUpMember(MemberVO member) {
    boolean isSuccess;
    member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
    try {
      isSuccess =  memberMapper.signUpMember(member);
      } catch (Exception e) {
        return false;
    }
    return isSuccess;
  }

	public List<Object> searchMemberContents(String nickname) {
    List<Object> memberContents = null;
    try {
      memberContents = memberMapper.selectByMemberContent(nickname);
    } catch (Exception e) {
      return null;
    }
    return memberContents;
  }

  public boolean deleteMember(String memberId) {
    if(memberMapper.deleteMember(memberId) == 1) {
      return true;
    } else {
      return false;
    }
  }
}