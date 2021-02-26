package com.j.board.controller;

import com.j.board.domain.BoardVO;
import com.j.board.domain.MemberVO;
import com.j.board.security.CustomMember;
import com.j.board.service.MemberService;
import com.j.board.util.ResponseResult;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/member")
public class MemberController {

  final private MemberService memberService;
  private ResponseResult responseResult;

  public MemberController(MemberService memberService) {
    this.memberService = memberService;
  }

  @PostMapping("/signup")
  public ResponseEntity<ResponseResult> signUpMember(@RequestBody MemberVO member){
    boolean isSuceess =  memberService.signUpMember(member);
      if(isSuceess){
          responseResult = new ResponseResult("success");
        return new ResponseEntity<>(responseResult,HttpStatus.OK);
      }
      else{
          responseResult = new ResponseResult("ID is conflict");
        return new ResponseEntity<>(responseResult,HttpStatus.CONFLICT);
      }
  }
  @PostMapping("/nickname")
  public ResponseEntity<ResponseResult> getMemberNickname() {
    CustomMember user = (CustomMember) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    
    if(user == null){
        responseResult = new ResponseResult("not logined");
      return new ResponseEntity<>(responseResult, HttpStatus.NOT_FOUND);
    }  else {
        responseResult = new ResponseResult(user.getMember().getNickname());
      return new ResponseEntity<>(responseResult, HttpStatus.OK);
    }
  }

  @PostMapping("/mypage")
  public ResponseEntity<ResponseResult> getMyInfo(String password) {
    CustomMember user  = (CustomMember) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if(user == null) {
        responseResult = new ResponseResult("page not found");
      return new ResponseEntity<>(responseResult, HttpStatus.BAD_REQUEST);
    } else {
        memberService.getMyInfo(user, password);
        responseResult = new ResponseResult(user.getMember().toString());
      return new ResponseEntity<>(responseResult, HttpStatus.OK);
    }
  }

  @GetMapping("/content/{nickname}")
  public ResponseEntity<ResponseResult> getMemberInfo(@PathVariable("nickname") String nickname) {
    List<BoardVO> memberContents;
    memberContents = memberService.searchMemberContents(nickname);
    if(memberContents == null) {
        responseResult = new ResponseResult("not found member");
      return new ResponseEntity<>(responseResult ,HttpStatus.BAD_REQUEST);
    } else {
        responseResult = new ResponseResult(memberContents);
      return new ResponseEntity<>(responseResult, HttpStatus.OK);
    }
  }

  @PostMapping("/info/{memberid}")
  public ResponseEntity<ResponseResult> setMemberInfo(@PathVariable("memberid") String memberId, @RequestParam("password") String password, @RequestParam("nickname") String nickname) {
    CustomMember userPrincpal = (CustomMember)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    MemberVO member = new MemberVO();
    member.setMemberId(memberId);
    member.setNickname(nickname);
    if(memberService.modifyMember(userPrincpal, member)){
      responseResult = new ResponseResult("Success");
      return new ResponseEntity<>(responseResult, HttpStatus.OK);
    }
    responseResult = new ResponseResult("forbidden access");
    return new ResponseEntity<>(responseResult, HttpStatus.FORBIDDEN);
  }

  @DeleteMapping("")
  public ResponseEntity<ResponseResult> deleteMember(@PathVariable("nickname") String nickname, @RequestParam("password") String password ) {
    CustomMember user = (CustomMember) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if(user == null) {
      responseResult = new ResponseResult("forbidden access");
      return new ResponseEntity<>(responseResult, HttpStatus.FORBIDDEN);
    }
    
    String userId = user.getUsername();
    String userPassword = password;
    if(memberService.deleteMember(userId, userPassword)) {
      responseResult = new ResponseResult("Success");
      return new ResponseEntity<>(responseResult, HttpStatus.OK);
    } else {
      responseResult = new ResponseResult("Service currently unavailable");
      return new ResponseEntity<>(responseResult, HttpStatus.BAD_REQUEST);
    }

  }

}