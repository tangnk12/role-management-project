package com.example.fyp.demo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.fyp.demo.common.Constants;
import com.example.fyp.demo.common.Result;
import com.example.fyp.demo.controller.userDto.UserDTO;
import com.example.fyp.demo.entity.User;
import com.example.fyp.demo.exception.ServiceException;
import com.example.fyp.demo.mapper.UserMapper;
import com.example.fyp.demo.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.fyp.demo.utils.TokenUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tnk
 * @since 2022-08-30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
//    private static final Log LOG=Log.get();

    private static final Log log = Log.get();
    @Override
    public UserDTO login(UserDTO userDto) {
        User one=getUserInfo(userDto);

        if(one!=null){

            BeanUtil.copyProperties(one,userDto,true);
            //setting token
            String token=TokenUtil.genToken(one.getId().toString(),one.getPassword());
            userDto.setToken(token);
            return userDto;
        }
        else{
            throw new ServiceException(Constants.CODE_600,"password or username incorrect");
        }

    }
    @Override
    public User register(UserDTO userDto){
       User one=getUserInfo(userDto);
       if(one==null){
           one = new User();
           BeanUtil.copyProperties(userDto,one,true);
           save(one);

       }
       else{
           throw new ServiceException(Constants.CODE_600,"user exist");

       }
       return one;


    }
    private User getUserInfo(UserDTO userDto){

        QueryWrapper<User> queryWrapper= new QueryWrapper<>();
        queryWrapper.eq("username",userDto.getUsername());
        queryWrapper.eq("password",userDto.getPassword());
        User one;

        //method when find more account
//        List<User> list = list(queryWrapper);
//        return list.size()!=0;
        //or
        try{
            one=getOne(queryWrapper);
//           return one !=null;

        }
        catch(Exception e){
            log.error(e);

            throw new ServiceException(Constants.CODE_500,"system error ");

        }
        return one;


    }


}
