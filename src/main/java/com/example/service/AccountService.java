package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account registerUser(String username, String password){
        Optional<Account> check = accountRepository.findByUsername(username);
        if(check.isEmpty()){
            if(username != "" && password.length() >= 4){
                Account account = new Account(username, password);
                return accountRepository.save(account);
                
            }
        }
        
        return null;
       
    }

    public Account login(String username, String password){
        Optional<Account> check = accountRepository.findByUsernameAndPassword(username, password);
        if(check.isPresent()){
            return (Account) check.get();
        } else {
            return null;
        }
        
        
    }

}
