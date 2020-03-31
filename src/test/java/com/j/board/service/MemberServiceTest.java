package com.j.board.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.j.board.domain.BoardVO;
import com.j.board.domain.MemberVO;
import com.j.board.persistence.MemberMapper;

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
  @Mock
  private MemberVO memberVO;

  @Mock
  BCryptPasswordEncoder bCryptPasswordEncoder;

  @InjectMocks
  MemberServiceImpl memberService;

  List<BoardVO> boardVOs = new ArrayList<>();
  public LocalDate localTimeStd;

  @Before
  public void setUp() throws Exception {
    Timestamp today = new Timestamp(new Date().getTime());
    localTimeStd = LocalDate.of(2020, 1, 1);
    
    
    memberVO= new MemberVO("test","testpasswd","testname", today, today,"ROLE_USER");
    BoardVO postOne = new BoardVO();
    postOne.setBoardNum(1);
    postOne.setTitle("test title");
    postOne.setContent("content");
    postOne.setMemberId("id");
    postOne.setNickname("nickname");
    postOne.setWriteTime(localTimeStd.toString());
    postOne.setGoodCount(1);
    postOne.setReadCount(1);
    postOne.setFileAltName("test.jpg");
    postOne.setIp("127.0.0.1");

    boardVOs.add(0,postOne);
    
  }

  @Test
  public void signUpMemberTest() {
    when(memberMapper.signUpMember(memberVO)).thenReturn(true);
    boolean result = memberService.signUpMember(memberVO);
    
    assertEquals(true, result);
  }

  @Test
  public void searchMemberContentsTest() {
    when(memberMapper.selectByMemberContent("test")).thenReturn(boardVOs);
    assertEquals(boardVOs.get(0).getBoardNum(), memberService.searchMemberContents("test").get(0).getBoardNum());
  }
}