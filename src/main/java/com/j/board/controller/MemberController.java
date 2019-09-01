package com.j.board.controller;

import java.security.Principal;
import java.util.List;

import com.j.board.domain.MemberVO;
import com.j.board.security.CustomMember;
import com.j.board.service.MemberService;

import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {

  @Autowired
  MemberService memberService;

  @PostMapping("/signup")
  public HttpStatus signUpMember(@RequestBody MemberVO member){
    boolean isSuceess =  memberService.signUpMember(member);
      if(isSuceess){
        return HttpStatus.OK;
      }
      else{
        return HttpStatus.CONFLICT;
      }
  }
  @PostMapping("/nickname")
  public ResponseEntity<String> getMemberNickname(Principal principal) {
    CustomMember user = (CustomMember) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if(user == null){
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }  else {
      return new ResponseEntity<>(user.getMemberVO().getNickname(),HttpStatus.OK);
    }
  }

  @PostMapping("/info")
  public ResponseEntity<Object> getMemberInfo(String Nickname) {
    List<Object> memberContents = null;
    memberContents = memberService.searchMemberContents(Nickname);
    if(memberContents == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } else {
      return new ResponseEntity<>(memberContents, HttpStatus.OK);
    }
  }

  @Delete("delete")
  public HttpStatus deleteMember(Principal principal) {
    CustomMember user = (CustomMember) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if(user == null) {
      return HttpStatus.FORBIDDEN;
    } else {
      String userId = user.getMemberVO().getMemberId();
      if(memberService.deleteMember(userId)) {
        return HttpStatus.OK;
      } else {
        return HttpStatus.BAD_REQUEST;
      }
    }
  }

}