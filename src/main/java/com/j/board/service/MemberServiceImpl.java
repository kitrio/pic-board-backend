package com.j.board.service;

import java.util.List;

import com.j.board.domain.BoardVO;
import com.j.board.domain.MemberVO;
import com.j.board.persistence.MemberMapper;
import com.j.board.security.CustomMember;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class MemberServiceImpl implements MemberService {

  private final MemberMapper memberMapper;
  private final PasswordEncoder bCryptPasswordEncoder;
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  public MemberServiceImpl(MemberMapper memberMapper, PasswordEncoder bCryptPasswordEncoder) {
    this.memberMapper = memberMapper;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @Override
  public boolean signUpMember(MemberVO member) {
    boolean isSuccess;
    member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
    member.setAuthInfo("ROLE_USER");
    try {
      isSuccess = memberMapper.signUpMember(member);
    } catch (Exception e) {
      logger.error("not signed member", e);
      return false;
    }
    return isSuccess;
  }

  @Override
  public List<BoardVO> searchMemberContents(String nickname) {
    List<BoardVO> memberContents;
    try {
      memberContents = memberMapper.selectByMemberContent(nickname);
    } catch (Exception e) {
      logger.error("not found memberContents", e);
      return null;
    }
    return memberContents;
  }

  @Override
  public boolean modifyMember(MemberVO member) {
    return true;
  }

  @Override
  public MemberVO getMyInfo(CustomMember principal, String password) {
    String memberPassword = bCryptPasswordEncoder.encode(password);
    MemberVO member = null;
    if(principal.getPassword().equals(memberPassword)){
        member = memberMapper.selectMyInfo(principal.getUsername());
    }
    return member;
  }

  @Override
  public boolean deleteMember(String memberId) {
    return memberMapper.deleteMember(memberId) == 1;
  }


}