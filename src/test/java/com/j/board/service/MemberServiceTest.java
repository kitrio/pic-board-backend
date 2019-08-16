package com.j.board.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.Date;

import com.j.board.domain.MemberVO;
import com.j.board.persistence.MemberMapper;
import com.j.board.service.MemberServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class MemberServiceTest {
  @Mock
  private MemberMapper memberMapper;
  private MemberVO memberVO;

  @Mock
  BCryptPasswordEncoder bCryptPasswordEncoder;

  @InjectMocks
  MemberServiceImpl memberService;

  @Before
  public void setUp() throws Exception {
    Timestamp today = new Timestamp(new Date().getTime());
    memberVO = new MemberVO("test","testpasswd","testname", today, today,"ROLE_USER");
  }

  @Test
  public void signUpMemberTest() {
    when(memberMapper.signUpMember(memberVO)).thenReturn(true);
    boolean result = memberService.signUpMember(memberVO);
    
    assertEquals(true, result);
  }
}