package com.magicmond.global.numerologyreportservice.controller;


import com.magicmond.global.numerologyreportservice.entities.UserInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class test {


    @GetMapping("/submit")
    public ResponseEntity<?> createOrder (@RequestBody UserInfo userInfo){

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }



}
