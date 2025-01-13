package com.example.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.GeneralError;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

 @Controller
public class SocialMediaController {

    
    private final AccountService accountService;
    private final MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    

    @PostMapping("/register")
    public @ResponseBody ResponseEntity<Account> createAccount(@RequestBody Account newAccount){

       Account account = accountService.createAccount(newAccount.getUsername(), newAccount.getPassword());
        
        return ResponseEntity.ok().body(account);
        
    }

    @PostMapping("/login")
    public @ResponseBody ResponseEntity<Account> verifyLogin(@RequestBody Account account){
     Account loggedInAccount=  accountService.verifyLogin(account.getUsername(), account.getPassword());

        return ResponseEntity.ok().body(loggedInAccount);
    }


    @PostMapping("/messages")
    public @ResponseBody ResponseEntity<Message> newMessage(@RequestBody Message message){
        Message newMsg = messageService.createMessage(message.getPostedBy(), message.getMessageText(), message.getTimePostedEpoch());

        return ResponseEntity.ok().body(newMsg);
    }

    @GetMapping("/messages")
    public @ResponseBody ResponseEntity<List<Message>> getAllMessages(){
        List<Message> messageList = messageService.getAllMessages();

        return ResponseEntity.ok().body(messageList);
    }


    @GetMapping("/messages/{messageId}")
    public @ResponseBody ResponseEntity<Optional<Message>> findById(@PathVariable int messageId){
       Optional<Message> message = messageService.findById(messageId);
       if(message.isPresent()){
        return ResponseEntity.ok().body(message);
       }
       else{
        return ResponseEntity.ok().body(null);
       }
    }


    @DeleteMapping("/messages/{messageId}")
    public @ResponseBody ResponseEntity<String> deleteMessage(@PathVariable int messageId){
       Optional<Message> message = messageService.findById(messageId);
       if(message.isPresent()){
        messageService.deleteMessage(messageId);
        return ResponseEntity.ok().body("1 row deleted");
       }
       else{
        return ResponseEntity.ok().body(null);
       }
}

@PatchMapping("/messages/{messageId}")
public @ResponseBody ResponseEntity<String> updateMessage(@PathVariable int messageId, @RequestBody Map<String,String> requestBody){
    
    String messageText = requestBody.get("messageText");
     messageService.updateMessage(messageId, messageText);
    return ResponseEntity.ok().body("1 row updated");
}

@GetMapping("accounts/{accountId}/messages")
public @ResponseBody ResponseEntity<List<Message>> getMessagesByAccountId(@PathVariable int accountId){
        List<Message> messageList = messageService.findByPostedBy(accountId);
        
            return ResponseEntity.ok().body(messageList);         
}

    
    
   
       
    
    



}
