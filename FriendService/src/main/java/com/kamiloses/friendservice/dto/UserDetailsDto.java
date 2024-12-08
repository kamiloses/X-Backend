package com.kamiloses.friendservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsDto {



    private String id;
    private String chatId;
    private String username;

    private String password;
    private Boolean isOnline;


    private String firstName;
    private String lastName;

    private String bio;

    private String profileImageUrl;


    private Set<String> tweetsIds;

    private Set<String> followersIds;

    private Set<String> followingIds;




}
