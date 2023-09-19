package com.example.fyp.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.fyp.demo.controller.userDto.UserDTO;
import com.example.fyp.demo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tnk
 * @since 2022-08-30
 */
public interface IUserService extends IService<User> {

    UserDTO login(UserDTO userDto);

    User register(UserDTO userDto);

//    void updatePassword(UserPasswordDto userPassword);

    Page<User> findPage(Page<User> objectPage, String username, String email, String address);
}
