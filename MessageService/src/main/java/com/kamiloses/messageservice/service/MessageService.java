package com.kamiloses.messageservice.service;

import com.kamiloses.messageservice.dto.MessageDto;
import com.kamiloses.messageservice.dto.UserDetailsDto;
import com.kamiloses.messageservice.repository.MessageRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final RabbitMessageProducer rabbitMessageProducer;

    public MessageService(MessageRepository messageRepository, RabbitMessageProducer rabbitMessageProducer) {
        this.messageRepository = messageRepository;
        this.rabbitMessageProducer = rabbitMessageProducer;
    }

    public Mono<List<MessageDto>> showMessageRelatedWithChat(String chatId) {
        return messageRepository.findByChatId(chatId).map(messageEntity -> {
            UserDetailsDto sender = rabbitMessageProducer.askForUserDetails(messageEntity.getSenderUsername());
            UserDetailsDto recipient = rabbitMessageProducer.askForUserDetails(messageEntity.getRecipientUsername());
            MessageDto messageDto = new MessageDto();
            messageDto.setSender(sender);
            messageDto.setRecipient(recipient);
            messageDto.setContent(messageEntity.getContent());
            messageDto.setTimestamp(messageEntity.getTimestamp());
            return messageDto;
        }).collectList();

    }


    public Flux<MessageDto> showMessagesRelatedWithUser(String username) {
        return messageRepository.findBySenderUsernameOrRecipientUsername(username, username)
                .map(messageEntity -> {
                    UserDetailsDto sender = rabbitMessageProducer.askForUserDetails(messageEntity.getSenderUsername());
                    UserDetailsDto recipient = rabbitMessageProducer.askForUserDetails(messageEntity.getRecipientUsername());
                    MessageDto messageDto = new MessageDto();
                    messageDto.setChatId(messageEntity.getChatId());
                    messageDto.setSender(sender);
                    messageDto.setRecipient(recipient);
                    messageDto.setContent(messageEntity.getContent());
                    messageDto.setTimestamp(messageEntity.getTimestamp());
                    return messageDto;


                });}




}
