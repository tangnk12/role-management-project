package com.example.fyp.demo.service;

import com.example.fyp.demo.controller.userDto.UserDTO;
import com.example.fyp.demo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  service class
 * </p>
 *
 * @author tnk
 * @since 2022-08-30
 */
public interface IUserService extends IService<User> {

    UserDTO login(UserDTO userDto);

    User register(UserDTO userDto);
}
