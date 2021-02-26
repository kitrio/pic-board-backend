package com.j.board.controller;

import com.j.board.domain.BoardVO;
import com.j.board.security.CustomMember;
import com.j.board.service.BoardListService;
import com.j.board.service.FileService;
import com.j.board.util.ResponseResult;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/list")
public class BoardController {

    private final FileService fileService;
    private final BoardListService boardListService;
    private ResponseResult responseResult;

    public BoardController(FileService fileService, BoardListService boardListService) {
        this.fileService = fileService;
        this.boardListService = boardListService;
    }

    @GetMapping("/best")
    public ResponseEntity<ResponseResult> getBestContents(@RequestParam("date") String date) {
        LocalDate dateOfWeek = LocalDate.parse(date,DateTimeFormatter.ISO_DATE);
        List<BoardVO> contents = boardListService.contentBestReadService(dateOfWeek);

        if(contents == null){
            responseResult = new ResponseResult("Service currently unavailable");
            return new ResponseEntity<>(responseResult ,HttpStatus.BAD_REQUEST);
        }
        responseResult = new ResponseResult(contents);
        return new ResponseEntity<>(responseResult, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseResult> searchContents(@RequestParam("keyword") String keyword,
                                                 @RequestParam("startpage") int startPage, @RequestParam("endpage") int endPage) {
        List<BoardVO> contents = boardListService.contentSearchByTitle(keyword, startPage, endPage);
        if(contents == null){
            responseResult = new ResponseResult("Service currently unavailable");
            return new ResponseEntity<>(responseResult, HttpStatus.BAD_REQUEST);
        } 
        responseResult = new ResponseResult(contents);
        return new ResponseEntity<>(responseResult, HttpStatus.OK);
        
    }

    @GetMapping("/contents")
    public ResponseEntity<ResponseResult> getContentsList(@RequestParam("firstpage") int firstPage, @RequestParam("lastpage") int lastPage){
        List<BoardVO> contents = boardListService.contentListReadService(firstPage, lastPage);
        if(contents == null){
            responseResult = new ResponseResult("Service currently unavailable");
            return new ResponseEntity<>(responseResult, HttpStatus.BAD_REQUEST);
        }
        responseResult = new ResponseResult(contents);
        return new ResponseEntity<>(responseResult, HttpStatus.OK);
    }

    @PostMapping("/content")
    public ResponseEntity<ResponseResult> writeContent(@RequestBody BoardVO contentVO, HttpServletRequest request) {
        String ip = getIpAddress(request);
        CustomMember member = (CustomMember) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        contentVO.setMemberId(member.getUsername());
        contentVO.setNickname(member.getMember().getNickname());
        contentVO.setIp(ip);
        if(boardListService.contentWriteService(contentVO)){
            responseResult = new ResponseResult("Success");
            return new ResponseEntity<>(HttpStatus.OK);
        }
        responseResult = new ResponseResult("Service currently unavailable");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/content/image")
    public ResponseEntity<ResponseResult> uploadImg(@RequestPart("img") final MultipartFile imgfile) {

        String filePath = fileService.upLoadFile(imgfile);
        if(filePath.equals("invalidfile") || filePath.equals("fail")){
            responseResult = new ResponseResult("Service currently unavailable");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        responseResult = new ResponseResult(filePath);
        return new ResponseEntity<>(responseResult, HttpStatus.OK);
    }

    @GetMapping("/content/{num}")
    public ResponseEntity<ResponseResult> readContent(@PathVariable("num") int num, HttpServletRequest request) {
        BoardVO content = boardListService.contentReadService(num);
        if (content == null) {
            responseResult = new ResponseResult("Service currently unavailable");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Cookie[] cookies = request.getCookies();
        String visitdata = "|";
        String cookieName = "viewcount";
        boolean isVisit = false;
        ResponseCookie responseCookie;
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals(cookieName)) {
                    visitdata = cookies[i].getValue();
                    break;
                }
            }
        }

        if (visitdata.contains(num + "|")) {
            isVisit = true;
        } else {
            boardListService.contentCountUp(num);
        }

        if(isVisit) {
            responseCookie = ResponseCookie.from(cookieName, visitdata)
                    .sameSite("Lax")
                    .build();
        } else {
            responseCookie = ResponseCookie.from(cookieName, num + "|" + visitdata)
                    .sameSite("Lax")
                    .build();
        }
        responseResult = new ResponseResult(content);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(responseResult);
    }

    @PatchMapping("/content/")
    public ResponseEntity<ResponseResult> modifyContent(@RequestBody BoardVO contentVO) {
        if(boardListService.contentModifyService(contentVO)){
            responseResult = new ResponseResult("Success");
            return new ResponseEntity<>(responseResult, HttpStatus.OK);
        }
        responseResult = new ResponseResult("Service currently unavailable");
        return new ResponseEntity<>(responseResult, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/content/{num}")
    public ResponseEntity<ResponseResult> deleteContent(@PathVariable("num") int num){
        CustomMember user = (CustomMember) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String memberId = user.getUsername();
        if(boardListService.contentDelete(num, memberId)){
            responseResult = new ResponseResult("Success");
            return new ResponseEntity<>(responseResult, HttpStatus.OK);
        }
        responseResult = new ResponseResult("Page not found");
        return new ResponseEntity<>(responseResult, HttpStatus.BAD_REQUEST);
    }

    private String getIpAddress(HttpServletRequest request) {
        String remoteAddr = "";

        if(request == null){
            return remoteAddr;
        }

        remoteAddr = request.getHeader("X-FORWARDED-FOR");
        if(remoteAddr == null){
            return remoteAddr = request.getRemoteAddr();
        }
        return remoteAddr;
    }

    @PutMapping("/content/good/{num}")
    public ResponseEntity<ResponseResult> goodCount(@PathVariable("num") int boardNum, @AuthenticationPrincipal CustomMember principal) {
        if(principal == null){
            responseResult = new ResponseResult("Forbidden access");
            return new ResponseEntity<>(responseResult, HttpStatus.FORBIDDEN);
        }
        if(boardListService.contentGoodCount(boardNum, principal.getUsername())) {
            responseResult = new ResponseResult("Success");
            return new ResponseEntity<>(responseResult, HttpStatus.OK);
        }
        responseResult = new ResponseResult("Page not found");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}