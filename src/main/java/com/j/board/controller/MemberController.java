package com.j.board.controller;

import java.security.Principal;
import java.util.List;

import com.j.board.domain.BoardVO;
import com.j.board.domain.MemberVO;
import com.j.board.security.CustomMember;
import com.j.board.service.MemberService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/member")
public class MemberController {

  final private MemberService memberService;

  public MemberController(MemberService memberService) {
    this.memberService = memberService;
  }

  @PostMapping("/signup")
  public ResponseEntity<String> signUpMember(@RequestBody MemberVO member){
    boolean isSuceess =  memberService.signUpMember(member);
      if(isSuceess){
        return new ResponseEntity<>(HttpStatus.OK);
      }
      else{
        return new ResponseEntity<>(HttpStatus.CONFLICT);
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

  @GetMapping("/info/{nickname}")
  public ResponseEntity<Object> getMemberInfo(@PathVariable("nickname") String nickname) {
    List<BoardVO> memberContents;
    memberContents = memberService.searchMemberContents(nickname);
    if(memberContents == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } else {
      return new ResponseEntity<>(memberContents, HttpStatus.OK);
    }
  }

  @DeleteMapping("")
  public ResponseEntity<Object> deleteMember() {
    CustomMember user = (CustomMember) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if(user == null) {
      return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    } else {
      String userId = user.getMemberVO().getMemberId();
      if(memberService.deleteMember(userId)) {
        return new ResponseEntity<>(HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
    }
  }

}