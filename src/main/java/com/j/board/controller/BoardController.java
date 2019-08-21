 package com.j.board.controller;

 import javax.servlet.http.HttpServletRequest;

 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestBody;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RestController;

 import com.j.board.domain.BoardVO;
 import com.j.board.service.BoardListService;

 @RestController
 @RequestMapping("list")
 public class BoardController {
 	@Autowired
 	BoardListService boardListService;
     @PostMapping("/write")
     public void writeContent(@RequestBody BoardVO contentVO,HttpServletRequest request) {
     	String ip = getIpAddress(request);
         contentVO.setIp(ip);
         boardListService.writeService(contentVO);
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