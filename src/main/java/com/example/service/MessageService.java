package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public List<Message> getMessages(){
        return messageRepository.findAll();
    }

    public Message getMessageById(int message_Id){
        Optional<Message> optionalMessage = messageRepository.findById(message_Id);
        if(optionalMessage.isPresent()){
            return optionalMessage.get();
        } else {
            return null; 
        }
    }

    public Message patchMessageByID(Message message , int message_Id){
        //check if message is null 
        if(message.getMessage_text().trim()=="" || message.getMessage_text().length() > 255){
            return null; 
        }
        Optional<Message> optionalMessage = messageRepository.findById(message_Id);
        if(optionalMessage.isPresent()){
            Message updatedMessage = optionalMessage.get(); 
            updatedMessage.setMessage_text(message.getMessage_text());
            return messageRepository.save(updatedMessage); 
        } else{
            return null; 
        }

    }

    public boolean deleteMessageByIdHandler(int message_id) {
        Optional<Message> optionalMessage = messageRepository.findById(message_id);
        if (optionalMessage.isPresent()) {
            messageRepository.deleteById(message_id);
            return true; // Message existed and was deleted
        } else {
            return false; // Message did not exist
        }
    }

    public List<Message> getMessagesByAccountId(int account_id){
        List<Message> listOfMessage = messageRepository.findAll();
        List<Message> listOfUpdatedMessage = new ArrayList();
        for (Message message : listOfMessage){
            if (message.getPosted_by().equals(account_id)){
                listOfUpdatedMessage.add(message);
            }
        }
        return listOfUpdatedMessage;
    }

    

    

}
