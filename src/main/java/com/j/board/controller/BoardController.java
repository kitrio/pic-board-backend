package com.j.board.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.j.board.domain.BoardVO;
import com.j.board.security.CustomMember;
import com.j.board.service.BoardListService;
import com.j.board.service.FileService;

 @RestController
 @RequestMapping("/list")
 public class BoardController {
 	@Autowired
    BoardListService boardListService;
    
    @Autowired
    FileService fileService;

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
        LocalDate localDate = LocalDate.parse(date,DateTimeFormatter.ISO_DATE);
        List<BoardVO> contents = boardListService.contentBestReadService(localDate);
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
    public void writeContent(Principal principal, @RequestBody BoardVO contentVO, HttpServletRequest request) {
        String ip = getIpAddress(request);
        CustomMember user = (CustomMember) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        contentVO.setMemberId(user.getUsername());
        contentVO.setNickname(user.getMemberVO().getNickname());
        contentVO.setIp(ip);
        boardListService.contentWriteService(contentVO);
    }

    @PostMapping("/content/write/image")
    public ResponseEntity<String> uploadImg(Principal principal, @RequestPart("img") final MultipartFile imgfile) {

        String filePath = fileService.upLoadFile(imgfile);
        if(filePath.equals("invalidfile")){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(filePath, HttpStatus.OK);
    }

    @GetMapping("/content/{num}")
    public ResponseEntity<Object> readContent(@PathVariable("num") int num) {
        
        BoardVO content = boardListService.contentReadService(num);
        if(content == null ) {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
        }
        return new ResponseEntity<>(content, HttpStatus.OK);
    }
    
    @PutMapping("/content/good/{num}")
    public ResponseEntity<Object> goodCount(@PathVariable("num") int boardNum) {
        
        if(boardListService.contentGoodCount(boardNum) == 1) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PatchMapping("/content/update")
    public HttpStatus modifyContent(@RequestBody BoardVO contentVO) {
        if(boardListService.contentModifyService(contentVO) == 1){
            return HttpStatus.OK;
        }else {
            return HttpStatus.BAD_REQUEST;
        }
    }

    @DeleteMapping("/content/delete/{num}")
    public HttpStatus deleteContent(@PathVariable int num) {
        if(boardListService.contentDelete(num)){
            return HttpStatus.OK;
        }
        return HttpStatus.BAD_REQUEST;
    }

    private String getIpAddress(HttpServletRequest request) {
        String remoteAdr = "";
        if(request != null){
            remoteAdr = request.getHeader("X-FORWADED-FOR");
            if (remoteAdr == null || "".equals(remoteAdr)) {
                remoteAdr = request.getRemoteAddr();
            }
        }
        return remoteAdr;
    }

}