package com.example.fyp.demo.controller.userDto;

import com.example.fyp.demo.entity.Menu;
import lombok.Data;

import java.util.List;

/*
get user information login
 */
@Data
public class UserDTO {
    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private String avatarUrl;
    private String token;
    private String role;
    private List<Menu> menus;
}
