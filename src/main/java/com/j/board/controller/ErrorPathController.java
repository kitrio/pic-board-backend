package com.j.board.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorPathController implements ErrorController{

    @GetMapping("/error")
    public ResponseEntity<ErrorPathController> responseError(){
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @Override
    public String getErrorPath(){
        return "/error";
    }
}