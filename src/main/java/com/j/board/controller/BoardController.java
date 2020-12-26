package com.j.board.controller;

import com.j.board.domain.BoardVO;
import com.j.board.security.CustomMember;
import com.j.board.service.BoardListService;
import com.j.board.service.FileService;
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

    public BoardController(FileService fileService, BoardListService boardListService) {
        this.fileService = fileService;
        this.boardListService = boardListService;
    }

    @GetMapping("/contents")
    public ResponseEntity<Object> getContentsList(@RequestParam("firstpage") int firstPage, @RequestParam("lastpage") int lastPage){
        List<BoardVO> contents = boardListService.contentListReadService(firstPage, lastPage);
        if(contents == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(contents, HttpStatus.OK);
    }

    @GetMapping("/best")
    public ResponseEntity<Object> getBestContents(@RequestParam("date") String date) {
        LocalDate dateOfWeek = LocalDate.parse(date,DateTimeFormatter.ISO_DATE);
        List<BoardVO> contents = boardListService.contentBestReadService(dateOfWeek);
        if(contents == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(contents, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchContents(@RequestParam("keyword") String keyword,
                                                 @RequestParam("startpage") int startPage, @RequestParam("endpage") int endPage) {
        List<BoardVO> contents = boardListService.contentSearchByTitle(keyword, startPage, endPage);
        if(contents == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(contents, HttpStatus.OK);
        }
    }

    @PostMapping("/content/write")
    public ResponseEntity<Object> writeContent(@RequestBody BoardVO contentVO, HttpServletRequest request) {
        String ip = getIpAddress(request);
        CustomMember member = (CustomMember) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        contentVO.setMemberId(member.getUsername());
        contentVO.setNickname(member.getMember().getNickname());
        contentVO.setIp(ip);
        if(boardListService.contentWriteService(contentVO)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/content/write/image")
    public ResponseEntity<Object> uploadImg(@RequestPart("img") final MultipartFile imgfile) {

        String filePath = fileService.upLoadFile(imgfile);
        if(filePath.equals("invalidfile") || filePath.equals("fail")){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(filePath, HttpStatus.OK);
    }

    @GetMapping("/content/{num}")
    public ResponseEntity<Object> readContent(@PathVariable("num") int num, HttpServletRequest request) {
        BoardVO content = boardListService.contentReadService(num);
        if (content == null) {
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

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(content);
    }

    @PutMapping("/content/good/{num}")
    public ResponseEntity<Object> goodCount(@PathVariable("num") int boardNum, @AuthenticationPrincipal CustomMember principal) {
        if(principal == null)
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        if(boardListService.contentGoodCount(boardNum, principal.getUsername())) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PatchMapping("/content/update")
    public ResponseEntity<Object> modifyContent(@RequestBody BoardVO contentVO) {
        if(boardListService.contentModifyService(contentVO) == 1){
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/content/delete/{num}")
    public ResponseEntity<Object> deleteContent(@PathVariable("num") int num){
        CustomMember user = (CustomMember) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String memberId = user.getUsername();
        if(boardListService.contentDelete(num, memberId)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    private String getIpAddress(HttpServletRequest request) {
        String remoteAddr = "";
        if(request != null){
            remoteAddr = request.getHeader("X-FORWADED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        return remoteAddr;
    }

}