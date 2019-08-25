package com.j.board.controller;

import java.security.Principal;

import com.j.board.domain.MemberVO;
import com.j.board.security.CustomMember;
import com.j.board.service.MemberService;

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
  public ResponseEntity<MemberController> signUpMember(@RequestBody MemberVO member){
    boolean isSuceess =  memberService.signUpMember(member);
      if(isSuceess){
        return new ResponseEntity<>(HttpStatus.OK);
      }
      else{
        return new ResponseEntity<>(HttpStatus.CONFLICT);
      }
  }
  @PostMapping("/info")
  public ResponseEntity<String> getMemberNickname(Principal principal) {
    CustomMember user = (CustomMember) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if(user != null){
      return new ResponseEntity<>(user.getMemberVO().getNickname(),HttpStatus.OK);
    }  else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
  

}