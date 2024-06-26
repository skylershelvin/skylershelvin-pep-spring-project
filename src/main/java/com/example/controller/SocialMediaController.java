package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

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

    @Autowired
    private MessageService messageService;


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

    @PostMapping("/messages")
    public ResponseEntity<Message> postMessageHandler(@RequestBody Message message){
        Message postMessage = messageService.createMessage(message);
        if(postMessage != null){
            return ResponseEntity.ok(postMessage); 
        } else {
            return ResponseEntity.badRequest().body(null);
        }
        
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getMessageHandler(){
        List<Message> listOfMessages = messageService.getMessages();
        return ResponseEntity.ok().body(listOfMessages);
    }

    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> getMessageBYIdHandler(@PathVariable int message_id){
        Message messageByID = messageService.getMessageById(message_id); 
        if(messageByID != null){
            return ResponseEntity.ok().body(messageByID);
        }
            return ResponseEntity.status(200).build(); 
    }

    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<Integer> patchMessageById(@PathVariable int message_id, @RequestBody Message updatedMessage){
        Message message = messageService.patchMessageByID(updatedMessage, message_id);
        if(message != null){
            String [] lines = message.getMessage_text().split("\r|\n");
            return ResponseEntity.ok().body(lines.length); 
        } else {
            return ResponseEntity.status(400).body(0); 
        }
    }

    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<Integer> deleteMessageByIdHandler(@PathVariable int message_id) {
        boolean messageDeleted = messageService.deleteMessageByIdHandler(message_id);
        if (messageDeleted) {
            return ResponseEntity.ok().body(1); // Return 1 if the message existed and was deleted
        } else {
            return ResponseEntity.ok().build();
        }
    }

    @GetMapping("/accounts/{account_id}/messages")
public @ResponseBody ResponseEntity<List<Message>>  getMessagesByAccountId(@PathVariable int account_id){
    List<Message> listofMessage= messageService.getMessagesByAccountId(account_id);
    return ResponseEntity.status(200).body(listofMessage);
}
}