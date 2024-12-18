package com.kamiloses.friendservice.websockets;

import com.kamiloses.friendservice.dto.UserActivityDto;
import com.kamiloses.friendservice.service.RabbitFriendshipProducer;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component  @Slf4j
public class EventsUserAvailability {

    private final RedisTemplate<String, String> redisTemplate;
    private final SimpMessagingTemplate messagingTemplate;

    public EventsUserAvailability(RedisTemplate<String, String> redisTemplate, SimpMessagingTemplate messagingTemplate) {
        this.redisTemplate = redisTemplate;
        this.messagingTemplate = messagingTemplate;
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String regex = "username=\\[(.*?)]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(headerAccessor.toString());

        if (matcher.find()) {
            String username = matcher.group(1);
            String sessionId = headerAccessor.getSessionId();


            redisTemplate.opsForValue().set(sessionId,username);

            log.info("user: {} Connected", username);
            updateFriendStatus(username,true);

        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();


        String username = redisTemplate.opsForValue().get(sessionId);
        redisTemplate.delete(sessionId);

        log.info("user: {} disconnected", username);

        updateFriendStatus(username,false);


    }



    private void updateFriendStatus(String username, boolean isOnline) {
        UserActivityDto friendStatus = new UserActivityDto(username, isOnline);
        messagingTemplate.convertAndSend("/topic/public/friendsOnline", friendStatus);
    }







    @PreDestroy
    public void cleanUpTheRedisDB() {

        //todo sprawdz jak usunąc wszystkie elementy z redisa

        redisTemplate.getConnectionFactory().getConnection().flushAll();

    }



}
