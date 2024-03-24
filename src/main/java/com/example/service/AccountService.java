package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    @Autowired
    public AccountRepository accountRepository;

    public Account addAccount(Account account){
        List<Account> listOfAccounts = accountRepository.findAll();
        for(Account checkAccount : listOfAccounts){
            if(checkAccount.getUsername().equals(account.getUsername())){
                return null;
            }
        }
        return (Account)accountRepository.save(account);
    }

    public Account verifyAccount(Account account){
        List<Account> listofAccounts = accountRepository.findAll();
        for(Account checkAccount : listofAccounts){
            if(checkAccount.getUsername().equals(account.getUsername()) && checkAccount.getPassword().equals(account.getPassword())){
                return checkAccount;
            }
        }
        return null;
    }
}
