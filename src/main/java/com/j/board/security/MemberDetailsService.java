package com.j.board.security;

import com.j.board.domain.MemberVO;
import com.j.board.persistence.MemberMapper;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MemberDetailsService implements UserDetailsService {

  final private MemberMapper memberMapper;

  public MemberDetailsService(MemberMapper memberMapper) {
    this.memberMapper = memberMapper;
  }

  @Override
  public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
    MemberVO member = memberMapper.findByUserid(memberId);
    if(member == null ) {
      throw new UsernameNotFoundException("memberId is not found"+ memberId);
    }

    return new CustomMember(member);
  }
  
}