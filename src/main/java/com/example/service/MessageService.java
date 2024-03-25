package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository; 
    
    @Autowired
    AccountRepository accountRepository; 


    public Message createMessage(Message message){
        //check if message is null and if its over 255 characters. 
        if(message.getMessage_text().trim().equals("") || message.getMessage_text().length() > 255){
            return null; 
        }
        List<Account> account = accountRepository.findAll();
        for(Account checkAccount : account){
            if(checkAccount.getAccount_id().equals(message.getPosted_by())){
                return messageRepository.save(message);
            }
        }
        return null; 
    }

}
