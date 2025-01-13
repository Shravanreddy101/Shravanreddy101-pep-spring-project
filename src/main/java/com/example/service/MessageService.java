package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.exception.GeneralError;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public Message createMessage(int postedBy, String messageText, Long timePostedEpochLong){
        if(messageText.isBlank() || messageText.length() > 255 || !messageRepository.existsById(postedBy) ){
            throw new GeneralError("Sorry but message cannot be blank and has to be less than 255 characters");
        }
       
        Message message = new Message();
        message.setPostedBy(postedBy);
        message.setMessageText(messageText);
        message.setTimePostedEpoch(timePostedEpochLong);


        return messageRepository.save(message);
    }


    public List<Message> getAllMessages(){
       return (messageRepository.findAll());
    }

    public List<Message> findByPostedBy(int accountId){
        return messageRepository.findByPostedBy(accountId);
    }


    public Optional<Message> findById(int messageId){
        return messageRepository.findById(messageId);
    }

    public void deleteMessage(int messageId){
        messageRepository.deleteById(messageId); 
    }

    public void updateMessage(int messageId, String messageText){

        Optional<Message> message = messageRepository.findById(messageId);
        if(message.isEmpty()){
            throw new GeneralError("Message not found for that ID");
        }
        if(messageText.isBlank()){
            throw new GeneralError("Message can't be blank");
        }
        if(messageText.length()> 255){
           throw new GeneralError("Message cannot be greater than 255 characters");
        }

            Message updatedMessage = message.get();
            updatedMessage.setMessageText(messageText);
            messageRepository.save(updatedMessage);
    } 
}
