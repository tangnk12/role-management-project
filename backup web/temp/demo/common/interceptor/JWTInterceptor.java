package com.example.fyp.demo.common.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.fyp.demo.common.Constants;
import com.example.fyp.demo.entity.User;
import com.example.fyp.demo.exception.ServiceException;
import com.example.fyp.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class JWTInterceptor implements HandlerInterceptor {
    @Autowired
    private IUserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        String token = request.getHeader("token");
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        if(StrUtil.isBlank(token)){
            throw new ServiceException(Constants.CODE_401,"no token please login again");
        }
        String userId;
        try{
            userId= JWT.decode(token).getAudience().get(0);
        }
        catch(JWTDecodeException j){
            throw new ServiceException(Constants.CODE_401,"token verify failed");
        }
        User user=userService.getById(userId);
        //add token below
        if(user==null){
            throw new ServiceException(Constants.CODE_401,"user does not exist");
        }

        JWTVerifier jwtVerify= JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try{
            jwtVerify.verify(token);
        }
        catch(JWTVerificationException e){
            throw new ServiceException(Constants.CODE_401,"no permission");
        }
        return true;


    }

}
