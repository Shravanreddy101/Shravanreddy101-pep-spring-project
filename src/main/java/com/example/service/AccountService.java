package com.example.service;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.IllegalArgumentException; 
import com.example.entity.Account;
import com.example.exception.DuplicateUsername;
import com.example.exception.GeneralError;
import com.example.exception.LoginException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account createAccount(String username, String password) {

        if(accountRepository.existsByUsername(username)){
            throw new DuplicateUsername("Sorry but an account with that username already exist ");
        }
        else if(username.isBlank() || password.length() < 4){
            throw new IllegalArgumentException("Sorry but either username is blank or password is less than 4 characters");
        }
        
        else{
            Account newAccount = new Account();
            newAccount.setUsername(username);
            newAccount.setPassword(password);
            return accountRepository.save(newAccount);
        }
        
  
    }

    public Account verifyLogin(String username, String password){
        Optional<Account> account = accountRepository.findByUsernameAndPassword(username, password);
        if(account.isPresent()){
            return account.get();
        }
        else{
            throw new LoginException("Sorry but those credentials are invalid");
        }

    }

    
    
    
   
    
    
}
