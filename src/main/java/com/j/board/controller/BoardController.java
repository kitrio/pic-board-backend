package com.j.board.controller;

import java.security.Principal;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.j.board.domain.BoardVO;
import com.j.board.security.CustomMember;
import com.j.board.service.BoardListService;
import com.j.board.service.FileService;

 @RestController
 @RequestMapping("list")
 public class BoardController {
 	@Autowired
    BoardListService boardListService;
    
    @Autowired
    FileService fileService;

    @PostMapping("content/write")
    public void writeContent(Principal principal, @RequestBody BoardVO contentVO, HttpServletRequest request) {
        String ip = getIpAddress(request);
        CustomMember user = (CustomMember) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        contentVO.setMemberId(user.getMemberVO().getMemberId());
        contentVO.setNickname(user.getMemberVO().getNickname());
        contentVO.setIp(ip);
        boardListService.contentWriteService(contentVO);
    }

    @PostMapping("content/write/image")
    public ResponseEntity<String> uploadImg(@RequestPart("img") final MultipartFile imgfile) {

        final String filePath = fileService.upLoadFile(imgfile);
        return new ResponseEntity<>(filePath, HttpStatus.OK);
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