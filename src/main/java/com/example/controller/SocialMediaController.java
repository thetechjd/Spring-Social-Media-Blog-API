package com.example.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.hibernate.mapping.List;
import org.hibernate.mapping.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.ClientErrorException;
import com.example.exception.DuplicateUsernameException;
import com.example.exception.UnauthorizedException;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;

    }


    @PostMapping("register")
    public ResponseEntity<Account> register(@RequestBody Account account) throws DuplicateUsernameException {
        Account newAccount = accountService.registerUser(account.getUsername(), account.getPassword());
        if(newAccount != null){
            return ResponseEntity.status(200).body(newAccount);
        } else if (newAccount == null){
            throw new DuplicateUsernameException("Username already exists");
        } else {
            return ResponseEntity.status(400).body(null);
        }
       
    }

    @PostMapping("login")
    public ResponseEntity<Account> login(@RequestBody Account account) throws UnauthorizedException {
        Account existingAccount = accountService.login(account.getUsername(), account.getPassword());
        if(existingAccount != null){
            return ResponseEntity.status(HttpStatus.OK).body(existingAccount);
        } else {
            throw new UnauthorizedException("Invalid login");
        }
        
    }

    @PostMapping("messages")
    public ResponseEntity<Message> postMessage(@RequestBody Message message) throws ClientErrorException {
        Message newMessage = messageService.postMessage(message);
        if(newMessage != null){
            return ResponseEntity.status(HttpStatus.OK).body(newMessage);
        }
        throw new ClientErrorException("Bad Request");
    }


    @GetMapping("messages")
    public ResponseEntity<ArrayList<Message>> getAllMessages(){
        ArrayList<Message> messages = messageService.getAllMessages();
        return ResponseEntity.status(HttpStatus.OK).body(messages);
    } 

    @GetMapping("messages/{message_id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer message_id){
        Message message = messageService.getMessageById(message_id);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @GetMapping("accounts/{account_id}/messages")
    public ResponseEntity<ArrayList<Message>> getAllMessagesByUser(@PathVariable Integer account_id){
        ArrayList<Message> messages = messageService.getAllMessagesByUser(account_id);
        return ResponseEntity.status(HttpStatus.OK).body(messages);
    }

    @DeleteMapping("messages/{message_id}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable Integer message_id){
        Integer rowsDeleted = messageService.deleteMessage(message_id);
        if(rowsDeleted > 0){
            return ResponseEntity.status(HttpStatus.OK).body(rowsDeleted);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }  
    }

    @PatchMapping("messages/{message_id}")
    public ResponseEntity<Integer> updateMessage(@PathVariable Integer message_id, @RequestBody HashMap<String, String> requestBody) throws ClientErrorException{
        String message_text = requestBody.get("message_text");
        Integer rowsUpdated = messageService.updateMessage(message_id, message_text);
        if(rowsUpdated > 0){
            return ResponseEntity.status(HttpStatus.OK).body(rowsUpdated);
        } 
        throw new ClientErrorException("Message does not exist");

    }


    



}
