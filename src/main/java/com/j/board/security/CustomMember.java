package com.j.board.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.j.board.domain.MemberVO;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomMember extends User {
  
 private MemberVO memberVO;
  
  public MemberVO getMemberVO() {
    return memberVO;
  }

  public CustomMember(String username, String password, Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
  }

    public CustomMember(MemberVO vo) {
    super(vo.getMemberId(), vo.getPassword(), createUserRole(vo.getAuthInfo()));
      this.memberVO = vo;
  }

  private static final long serialVersionUID = 1L;
  
  private static List<GrantedAuthority> createUserRole(String authInfo) {
    List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority(authInfo));
    return authorities;
  }
}