package com.j.board.service;

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
      return isSuccess;
      
      } catch (Exception e) {

        return false;
    }
  }
}