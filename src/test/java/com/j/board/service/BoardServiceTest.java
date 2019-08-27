package com.j.board.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDate;

import com.j.board.domain.BoardVO;
import com.j.board.persistence.BoardMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
@RunWith(SpringRunner.class)
public class BoardServiceTest {

    @InjectMocks
    BoardListServiceImpl boardListService;
    @Mock
    BoardMapper boardMapper;
    @Mock
    BoardVO postOne;
  
    public LocalDate localTimeStd;

    @Before
    public void setUp() {
    localTimeStd = LocalDate.of(2019, 8, 26);
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

  }

  @Test
  public void contentReadServiceTest() {
    
    BoardVO result = new BoardVO();
    when(boardMapper.selectOneContent(1)).thenReturn(postOne);
    result = boardListService.contentReadService(1);
    assertEquals(postOne, result);
    }
}