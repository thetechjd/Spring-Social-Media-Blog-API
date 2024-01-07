package com.example.service;

import java.util.ArrayList;
import java.util.Optional;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    private MessageRepository messageRepository;
    private AccountRepository accountRepository;

    
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }



    public Message postMessage(Message message){
        Optional<Account> check = accountRepository.findByAccountId(message.getPosted_by());
        if(check.isPresent()){
            String text = message.getMessage_text();
            if(!text.isEmpty() && text.length() <= 255 ){
                Account user = check.get();
                Message newMessage = new Message (user.getAccount_id(), message.getMessage_text(), message.getTime_posted_epoch());
                return messageRepository.save(newMessage);
            }
        }
        return null;

    }

    public ArrayList<Message> getAllMessages(){
        return (ArrayList<Message>) messageRepository.findAll();
    }

    public Message getMessageById(Integer id){
        Optional<Message> msg = messageRepository.findById(id);
        if(msg.isPresent()){
            return msg.get();
        }
        return null;
    }

    public ArrayList<Message> getAllMessagesByUser(Integer accountId){
        Optional<ArrayList<Message>> msg = messageRepository.findAllUserMessages(accountId);
        if(msg.isPresent()){
            return msg.get();
        }
        return null;
    }

    public Integer deleteMessage(Integer messageId){
        Optional<Message> msg = messageRepository.findById(messageId);
        if(msg.isPresent()){
            messageRepository.deleteById(messageId);
            return 1;
        }
        return 0;
       
    }

    public Integer updateMessage(Integer messageId, String text){
        Optional<Message> msg = messageRepository.findById(messageId);
        if(msg.isPresent()){
           
            if(!text.isEmpty() && text.length() <= 255){
                Message message = msg.get();
                message.setMessage_text(text);
                messageRepository.save(message);
                return 1;
            }
        }
        return 0;
    }
}
