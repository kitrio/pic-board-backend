package com.j.board.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.j.board.domain.MemberVO;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomMember extends User {

  public CustomMember(final String username, final String password, final Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
  } //defalut constructor

  public CustomMember(final MemberVO vo) {
    super(vo.getMemberId(), vo.getPassword(), createUserRole(vo.getAuthInfo()));
  }

  private static final long serialVersionUID = 1L;
  
  private static List<GrantedAuthority> createUserRole(final String authInfo) {
    final List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority(authInfo));
    return authorities;
  }
}