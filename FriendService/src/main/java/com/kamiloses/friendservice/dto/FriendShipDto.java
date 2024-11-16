package com.kamiloses.friendservice.dto;

import com.kamiloses.friendservice.Status;
import com.kamiloses.friendservice.entity.FriendshipEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendShipDto {
    @Id
    private String id;

    private UserDetailsDto user;
    private UserDetailsDto friend;

    private LocalDateTime createdAt;
    private Status status;

    private List<String> messagesId;



}
