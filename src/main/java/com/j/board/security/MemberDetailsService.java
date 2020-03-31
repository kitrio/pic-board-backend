package com.j.board.security;

import com.j.board.domain.MemberVO;
import com.j.board.persistence.MemberMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MemberDetailsService implements UserDetailsService {
  @Autowired
  private MemberMapper memberMapper;

  @Override
  public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
    MemberVO vo;
    try {
      vo = memberMapper.findByUserid(memberId);
    } catch (UsernameNotFoundException e) {
      //TODO error handler
      return null;
    }
    return new CustomMember(vo);
  }
  
}