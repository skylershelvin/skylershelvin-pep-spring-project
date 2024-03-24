package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.service.AccountService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
@RequestMapping("")
public class SocialMediaController {

    @Autowired
    private AccountService accountService;


    @PostMapping("/register")
    public ResponseEntity<Account> registerHandler(@RequestBody Account account){
        if (account.getUsername().trim().equals("") || account.getPassword().length() < 4) {
            return ResponseEntity.badRequest().body(null); // 400 Bad Request
        }
        Account newAccount = accountService.addAccount(account);
        if (newAccount != null) {
            return ResponseEntity.ok(newAccount); // 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // 409 Conflict
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Account> postLoginHandler(@RequestBody Account account){
        Account verifyAccount = accountService.verifyAccount(account);
        if (verifyAccount != null) {
            return ResponseEntity.ok(verifyAccount); // 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // 401 Unauthorized
        }
    }
}