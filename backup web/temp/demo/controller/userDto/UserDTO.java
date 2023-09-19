package com.example.fyp.demo.controller.userDto;

import lombok.Data;
/*
get user information login
 */
@Data
public class UserDTO {
    private String username;
    private String password;
    private String nickname;
    private String avatarUrl;
    private String token;
}
