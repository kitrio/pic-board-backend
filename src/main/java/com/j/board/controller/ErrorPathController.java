package com.j.board.controller;

import com.j.board.util.ResponseResult;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorPathController implements ErrorController{

    @GetMapping("/error")
    public ResponseEntity<ResponseResult> responseError(){
        ResponseResult responseBody  = new ResponseResult("page not found");
        return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
    }
    
    @Override
    public String getErrorPath(){
        return "/error";
    }
}