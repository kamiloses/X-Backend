package com.kamiloses.messageservice.service;

import com.kamiloses.messageservice.entity.MessageEntity;
import com.kamiloses.messageservice.repository.MessageRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class MessageInitialization {

private MessageRepository messageRepository;

    public MessageInitialization(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @PostConstruct
    public void init() {
     if (messageRepository.findAll().collectList().block().size()==0){
         MessageEntity messageEntity1 = new MessageEntity("1","1","kamil","Jan","hej",new Date(),false);
         MessageEntity messageEntity2 = new MessageEntity("2","1","Jan","kamil","cześć",new Date(),false);

          messageRepository.saveAll(List.of(messageEntity1,messageEntity2)).collectList().subscribe();
     }


}











}
